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
}
