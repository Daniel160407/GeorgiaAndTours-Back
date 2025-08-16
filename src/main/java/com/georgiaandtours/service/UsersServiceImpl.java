package com.georgiaandtours.service;

import com.georgiaandtours.dto.UserDto;
import com.georgiaandtours.exception.InvalidEmailOrPasswordException;
import com.georgiaandtours.exception.UserIsAlreadyRegisteredException;
import com.georgiaandtours.model.User;
import com.georgiaandtours.repository.UsersRepository;
import com.georgiaandtours.util.ModelConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .map(user -> {
                    throw new UserIsAlreadyRegisteredException("User with same email is already registered!");
                })
                .orElseGet(() -> usersRepository.save(modelConverter.convert(userDto)));
    }

    @Override
    public void saveUserSid(String email, String sid) {
        Optional<User> userOptional = usersRepository.findByEmail(email);
        userOptional.ifPresent(user -> {
            user.setSid(sid);
            usersRepository.save(user);
        });
    }

    @Override
    @Transactional
    public void updatePositionFor(String email) {
        List<User> users = usersRepository.findAllByOrderByPositionAsc();

        Optional<User> userToMove = users.stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst();

        if (userToMove.isEmpty()) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }

        User foundUser = userToMove.get();
        users.remove(foundUser);

        for (User user : users) {
            user.setPosition(user.getPosition() + 1);
        }

        foundUser.setPosition(0);
        users.addFirst(foundUser);

        usersRepository.saveAll(users);
    }

    @Override
    public String getUserSidByEmail(String email) {
        return usersRepository.findByEmail(email)
                .map(User::getSid)
                .orElse(null);
    }

    @Override
    public List<UserDto> getUsers() {
        return modelConverter.convertUsersToDtoList(
                usersRepository.findAll().stream()
                        .sorted(Comparator.comparing(User::getPosition))
                        .collect(Collectors.toList())
        );
    }
}
