package com.georgiaandtours.service;

import com.georgiaandtours.dto.MessageDto;
import com.georgiaandtours.exception.UserWithProvidedIdOrEmailNotFoundException;
import com.georgiaandtours.model.Message;
import com.georgiaandtours.model.User;
import com.georgiaandtours.repository.MessagesRepository;
import com.georgiaandtours.repository.UsersRepository;
import com.georgiaandtours.util.ModelConverter;
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
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MessagesServiceImpl implements MessagesService {
    private final MessagesRepository messagesRepository;
    private final UsersRepository usersRepository;
    private final ModelConverter modelConverter;

    // Create a flexible formatter that handles both with and without microseconds
    private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true) // handles 0-6 digits of microseconds
            .toFormatter();

    @Autowired
    public MessagesServiceImpl(MessagesRepository messagesRepository, UsersRepository usersRepository, ModelConverter modelConverter) {
        this.messagesRepository = messagesRepository;
        this.usersRepository = usersRepository;
        this.modelConverter = modelConverter;
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
        Message message = modelConverter.convert(messageDto);
        messagesRepository.save(message);
        return combineMessagesByEmail(messageDto.getSenderEmail());
    }

    @Override
    public List<MessageDto> editMessage(MessageDto messageDto) {
        Optional<Message> messageOptional = messagesRepository.findById(messageDto.getId());
        messageOptional.ifPresent(message -> {
            message.setDate(LocalDateTime.now().format(formatter));
            message.setPayload(messageDto.getPayload());
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

    private List<MessageDto> combineMessagesByEmail(String email) {
        List<Message> sentMessages = messagesRepository.findAllBySenderEmail(email);
        List<Message> receivedMessages = messagesRepository.findAllByReceiverEmail(email);

        List<Message> combinedMessages = new ArrayList<>();
        combinedMessages.addAll(sentMessages);
        combinedMessages.addAll(receivedMessages);

        combinedMessages.sort(Comparator.comparing(
                m -> LocalDateTime.parse(m.getDate(), formatter),
                Comparator.reverseOrder()
        ));

        return modelConverter.convertMessagesToDtoList(combinedMessages);
    }
}