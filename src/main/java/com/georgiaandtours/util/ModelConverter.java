package com.georgiaandtours.util;

import com.georgiaandtours.dto.CommentDto;
import com.georgiaandtours.dto.MessageDto;
import com.georgiaandtours.dto.TourDto;
import com.georgiaandtours.dto.UserDto;
import com.georgiaandtours.model.Comment;
import com.georgiaandtours.model.Message;
import com.georgiaandtours.model.Tour;
import com.georgiaandtours.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelConverter {
    public Message convert(MessageDto messageDto) {
        return Message.builder()
                .senderEmail(messageDto.getSenderEmail())
                .receiverEmail(messageDto.getReceiverEmail())
                .sender(messageDto.getSender())
                .receiver(messageDto.getReceiver())
                .date(messageDto.getDate())
                .payload(messageDto.getPayload())
                .build();
    }

    public User convert(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .position(0)
                .sid("")
                .build();
    }

    public List<UserDto> convertUsersToDtoList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(
                UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .position(user.getPosition())
                        .sid(user.getSid())
                        .build()
        ));
        return userDtos;
    }

    public List<MessageDto> convertMessagesToDtoList(List<Message> messages) {
        List<MessageDto> messageDtos = new ArrayList<>();
        messages.forEach(message -> messageDtos.add(
                MessageDto.builder()
                        .id(message.getId())
                        .senderEmail(message.getSenderEmail())
                        .receiverEmail(message.getReceiverEmail())
                        .sender(message.getSender())
                        .receiver(message.getReceiver())
                        .date(message.getDate())
                        .payload(message.getPayload())
                        .build()
        ));

        return messageDtos;
    }
}
