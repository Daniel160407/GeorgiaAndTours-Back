package com.georgiaandtours.controller;

import com.georgiaandtours.dto.UserDto;
import com.georgiaandtours.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/georgiaandtours/user")
@CrossOrigin(origins = "*")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<UserDto> userDtos = usersService.getUsers();
        return ResponseEntity.ok(userDtos);
    }

    @PutMapping
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        usersService.login(userDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        usersService.register(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
