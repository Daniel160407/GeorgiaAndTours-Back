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
    public Tour convert(TourDto tourDto) {
        return Tour.builder()
                .id(tourDto.getId())
                .name(tourDto.getName())
                .description(tourDto.getDescription())
                .requirements(tourDto.getRequirements())
                .price(tourDto.getPrice())
                .duration(tourDto.getDuration())
                .direction(tourDto.getDirection())
                .language(tourDto.getLanguage())
                .badge(tourDto.getBadge())
                .imageUrl(tourDto.getImageUrl())
                .build();
    }

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

    public Comment convert(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .tourId(commentDto.getTourId())
                .name(commentDto.getName())
                .date(commentDto.getDate())
                .rating(commentDto.getRating())
                .payload(commentDto.getPayload())
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

    public List<TourDto> convertToursToDtoList(List<Tour> tours) {
        List<TourDto> tourDtos = new ArrayList<>();
        tours.forEach(tour -> tourDtos.add(
                TourDto.builder()
                        .id(tour.getId())
                        .name(tour.getName())
                        .description(tour.getDescription())
                        .requirements(tour.getRequirements())
                        .price(tour.getPrice())
                        .duration(tour.getDuration())
                        .direction(tour.getDirection())
                        .language(tour.getLanguage())
                        .badge(tour.getBadge())
                        .imageUrl(tour.getImageUrl())
                        .build()
        ));

        return tourDtos;
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

    public List<CommentDto> convertCommentsToDtoList(List<Comment> comments) {
        List<CommentDto> commentDtos = new ArrayList<>();
        comments.forEach(comment -> commentDtos.add(
                CommentDto.builder()
                        .id(comment.getId())
                        .name(comment.getName())
                        .date(comment.getDate())
                        .rating(comment.getRating())
                        .payload(comment.getPayload())
                        .build()
        ));

        return commentDtos;
    }
}
