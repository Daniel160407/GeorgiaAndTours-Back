package com.georgiaandtours.service;

import com.georgiaandtours.dto.MessageDto;
import com.georgiaandtours.exception.UserWithProvidedIdOrEmailNotFoundException;
import com.georgiaandtours.mapper.MessageMapper;
import com.georgiaandtours.model.Message;
import com.georgiaandtours.repository.MessagesRepository;
import com.georgiaandtours.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MessagesServiceImpl implements MessagesService {
    private final MessagesRepository messagesRepository;
    private final UsersRepository usersRepository;
    private final MessageMapper messageMapper;

    private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .toFormatter();

    private final DateTimeFormatter lenientFormatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd[ HH:mm[:ss[.SSSSSS]]]")
            .toFormatter();

    @Autowired
    public MessagesServiceImpl(MessagesRepository messagesRepository, UsersRepository usersRepository, MessageMapper messageMapper) {
        this.messagesRepository = messagesRepository;
        this.usersRepository = usersRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public List<MessageDto> getMessagesByIdOrEmail(Integer id, String email) {
        if (id == null && email == null) {
            throw new IllegalArgumentException("Either id or email must be provided");
        }

        if (email != null) {
            return combineMessagesByEmail(email);
        }

        return usersRepository.findById(id)
                .map(user -> combineMessagesByEmail(user.getEmail()))
                .orElseThrow(() -> new UserWithProvidedIdOrEmailNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<MessageDto> addMessage(MessageDto messageDto) {
        messageDto.setDate(LocalDateTime.now().format(formatter));
        Message message = messageMapper.toEntity(messageDto);
        messagesRepository.save(message);
        return combineMessagesByEmail(messageDto.getSenderEmail());
    }

    @Override
    public List<MessageDto> editMessage(MessageDto messageDto) {
        Optional<Message> messageOptional = messagesRepository.findById(messageDto.getId());
        messageOptional.ifPresent(message -> {
            messageDto.setDate(LocalDateTime.now().format(formatter));
            messageMapper.updateMessageFromDto(message, messageDto);

            messagesRepository.save(message);
        });
        return combineMessagesByEmail(messageDto.getSenderEmail());
    }

    @Override
    public List<MessageDto> deleteMessage(Integer id) {
        String senderEmail = messagesRepository.findById(id).get().getSenderEmail();
        messagesRepository.deleteById(id);
        return combineMessagesByEmail(senderEmail);
    }

    private LocalDateTime parseDateTime(String dateString) {
        try {
            return LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            try {
                return LocalDateTime.parse(dateString, lenientFormatter);
            } catch (Exception ex) {
                if (dateString.contains(" ")) {
                    String datePart = dateString.split(" ")[0];
                    return LocalDateTime.parse(datePart + "T00:00:00");
                } else {
                    return LocalDateTime.parse(dateString + "T00:00:00");
                }
            }
        }
    }

    private List<MessageDto> combineMessagesByEmail(String email) {
        List<Message> sentMessages = messagesRepository.findAllBySenderEmail(email);
        List<Message> receivedMessages = messagesRepository.findAllByReceiverEmail(email);

        List<Message> combinedMessages = new ArrayList<>();
        combinedMessages.addAll(sentMessages);
        combinedMessages.addAll(receivedMessages);

        combinedMessages.sort(Comparator.comparing(
                m -> parseDateTime(m.getDate()),
                Comparator.reverseOrder()
        ));

        return messageMapper.toDtoList(combinedMessages);
    }
}