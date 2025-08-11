package com.georgiaandtours.controller;

import com.georgiaandtours.dto.MessageDto;
import com.georgiaandtours.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/messages")
@CrossOrigin(origins = "*")
public class MessageController {
    private final MessagesService messagesService;

    @Autowired
    public MessageController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @GetMapping("{email}")
    public ResponseEntity<?> getMessages(@PathVariable String email) {
        List<MessageDto> messageDtos = messagesService.getMessagesByEmail(email);
        return ResponseEntity.ok(messageDtos);
    }

    @PutMapping
    public ResponseEntity<?> editMessage(@RequestBody MessageDto messageDto) {
        List<MessageDto> messageDtos = messagesService.editMessage(messageDto);
        return ResponseEntity.ok(messageDtos);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer id) {
        List<MessageDto> messageDtos = messagesService.deleteMessage(id);
        return ResponseEntity.ok(messageDtos);
    }
}
