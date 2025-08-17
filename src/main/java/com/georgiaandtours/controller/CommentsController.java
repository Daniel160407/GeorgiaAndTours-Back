package com.georgiaandtours.controller;

import com.georgiaandtours.dto.CommentDto;
import com.georgiaandtours.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/georgiaandtours/comments")
@CrossOrigin(origins = "*")
public class CommentsController {
    private final CommentsService commentsService;

    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getComments(@PathVariable Integer id) {
        List<CommentDto> commentDtos = commentsService.getCommentsByTourId(id);
        return ResponseEntity.ok(commentDtos);
    }

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
        List<CommentDto> commentDtos = commentsService.addComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDtos);
    }

    @PutMapping
    public ResponseEntity<?> editComment(@RequestBody CommentDto commentDto) {
        List<CommentDto> commentDtos = commentsService.editComment(commentDto);
        return ResponseEntity.ok(commentDtos);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        List<CommentDto> commentDtos = commentsService.deleteComment(id);
        return ResponseEntity.ok(commentDtos);
    }
}
