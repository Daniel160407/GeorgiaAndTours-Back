package com.georgiaandtours.service;

import com.georgiaandtours.dto.UserDto;
import com.georgiaandtours.exception.InvalidEmailOrPasswordException;
import com.georgiaandtours.exception.UserIsAlreadyRegisteredException;
import com.georgiaandtours.repository.UsersRepository;
import com.georgiaandtours.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final ModelConverter modelConverter;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, ModelConverter modelConverter, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelConverter = modelConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void login(UserDto userDto) {
        usersRepository.findByEmail(userDto.getEmail())
                .filter(user -> passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
                .orElseThrow(() -> new InvalidEmailOrPasswordException("Invalid email or password!"));
    }

    @Override
    public void register(UserDto userDto) {
        usersRepository.findByEmail(userDto.getEmail())
                .map(user -> { throw new UserIsAlreadyRegisteredException("User with same email is already registered!"); })
                .orElseGet(() -> usersRepository.save(modelConverter.convert(userDto)));
    }
}
