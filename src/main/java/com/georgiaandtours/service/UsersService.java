package com.georgiaandtours.service;

import com.georgiaandtours.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {
    void login(UserDto userDto);

    void register(UserDto userDto);

    void saveUserSid(String email, String sid);

    void updatePositionFor(String email);

    String getUserSidByEmail(String email);

    List<UserDto> getUsers();
}
