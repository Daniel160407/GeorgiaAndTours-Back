package com.georgiaandtours.service;

import com.georgiaandtours.dto.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentsService {
    List<CommentDto> getCommentsByTourId(Integer id);

    List<CommentDto> addComment(CommentDto commentDto);

    List<CommentDto> editComment(CommentDto commentDto);

    List<CommentDto> deleteComment(Integer id);
}
