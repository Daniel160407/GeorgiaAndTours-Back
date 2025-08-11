package com.georgiaandtours.service;

import com.georgiaandtours.dto.MessageDto;
import com.georgiaandtours.model.Message;
import com.georgiaandtours.repository.MessagesRepository;
import com.georgiaandtours.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MessagesServiceImpl implements MessagesService {
    private final MessagesRepository messagesRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public MessagesServiceImpl(MessagesRepository messagesRepository, ModelConverter modelConverter) {
        this.messagesRepository = messagesRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<MessageDto> getMessagesByEmail(String email) {
        return combineMessagesByEmail(email);
    }

    @Override
    public List<MessageDto> addMessage(MessageDto messageDto) {
        String time = getLocalTime();

        messageDto.setDate(time);
        Message message = modelConverter.convert(messageDto);
        messagesRepository.save(message);

        return combineMessagesByEmail(messageDto.getSenderEmail());
    }

    @Override
    public List<MessageDto> editMessage(MessageDto messageDto) {
        Optional<Message> messageOptional = messagesRepository.findById(messageDto.getId());
        messageOptional.ifPresent(message -> {
            message.setDate(getLocalTime());
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        combinedMessages.sort(Comparator.comparing(
                m -> LocalDateTime.parse(m.getDate(), formatter),
                Comparator.reverseOrder()
        ));

        return modelConverter.convertMessagesToDtoList(combinedMessages);
    }

    private String getLocalTime() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        return localDate + " " + localTime;
    }
}
