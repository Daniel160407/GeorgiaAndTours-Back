package com.georgiaandtours.service;

import com.georgiaandtours.dto.MessageDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessagesService {
    List<MessageDto> getMessagesByIdOrEmail(Integer id, String email);

    List<MessageDto> addMessage(MessageDto messageDto);

    List<MessageDto> editMessage(MessageDto messageDto);

    List<MessageDto> deleteMessage(Integer id);
}
