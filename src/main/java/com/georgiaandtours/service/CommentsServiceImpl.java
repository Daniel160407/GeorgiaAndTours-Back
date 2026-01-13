package com.georgiaandtours.service;

import com.georgiaandtours.dto.CommentDto;
import com.georgiaandtours.mapper.CommentMapper;
import com.georgiaandtours.model.Comment;
import com.georgiaandtours.repository.CommentsRepository;
import com.georgiaandtours.util.ModelConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements CommentsService {
    private final CommentsRepository commentsRepository;
    private final CommentMapper commentMapper;

    private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm")
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
            .toFormatter();

    @Autowired
    public CommentsServiceImpl(CommentsRepository commentsRepository, CommentMapper commentMapper) {
        this.commentsRepository = commentsRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<CommentDto> getCommentsByTourId(Integer id) {
        List<Comment> comments = commentsRepository.findAllByTourId(id);
        return commentMapper.toDtoList(comments);
    }

    @Override
    public List<CommentDto> addComment(CommentDto commentDto) {
        commentDto.setDate(LocalDateTime.now().format(formatter));
        Comment convertedComment = commentMapper.toEntity(commentDto);
        commentsRepository.save(convertedComment);

        List<Comment> comments = commentsRepository.findAllByTourId(commentDto.getTourId());
        return commentMapper.toDtoList(comments);
    }

    @Override
    public List<CommentDto> editComment(CommentDto commentDto) {
        Optional<Comment> commentOptional = commentsRepository.findById(commentDto.getId());
        commentOptional.ifPresent(comment -> {
            commentMapper.updateCommentFromDto(comment, commentDto);
            commentsRepository.save(comment);
        });

        List<Comment> comments = commentsRepository.findAllByTourId(commentDto.getTourId());
        return commentMapper.toDtoList(comments);
    }

    @Override
    @Transactional
    public List<CommentDto> deleteComment(Integer id) {
        commentsRepository.deleteById(id);

        List<Comment> comments = commentsRepository.findAllByTourId(id);
        return commentMapper.toDtoList(comments);
    }
}
