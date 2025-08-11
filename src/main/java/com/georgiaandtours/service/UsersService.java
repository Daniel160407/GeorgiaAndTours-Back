package com.georgiaandtours.service;

import com.georgiaandtours.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UsersService {
    void login(UserDto userDto);

    void register(UserDto userDto);
}
