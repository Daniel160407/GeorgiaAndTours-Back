package com.georgiaandtours.service;

import com.georgiaandtours.dto.CommentDto;
import com.georgiaandtours.model.Comment;
import com.georgiaandtours.repository.CommentsRepository;
import com.georgiaandtours.util.ModelConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements CommentsService {
    private final CommentsRepository commentsRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public CommentsServiceImpl(CommentsRepository commentsRepository, ModelConverter modelConverter) {
        this.commentsRepository = commentsRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<CommentDto> getCommentsByTourId(Integer id) {
        List<Comment> comments = commentsRepository.findAllByTourId(id);
        return modelConverter.convertCommentsToDtoList(comments);
    }

    @Override
    public List<CommentDto> addComment(CommentDto commentDto) {
        Comment convertedComment = modelConverter.convert(commentDto);
        commentsRepository.save(convertedComment);

        List<Comment> comments = commentsRepository.findAllByTourId(commentDto.getTourId());
        return modelConverter.convertCommentsToDtoList(comments);
    }

    @Override
    public List<CommentDto> editComment(CommentDto commentDto) {
        Optional<Comment> commentOptional = commentsRepository.findById(commentDto.getId());
        commentOptional.ifPresent(comment -> {
            comment.setName(commentDto.getName());
            comment.setDate(commentDto.getDate());
            comment.setRating(commentDto.getRating());
            comment.setPayload(commentDto.getPayload());

            commentsRepository.save(comment);
        });

        List<Comment> comments = commentsRepository.findAllByTourId(commentDto.getTourId());
        return modelConverter.convertCommentsToDtoList(comments);
    }

    @Override
    @Transactional
    public List<CommentDto> deleteComment(Integer id) {
        commentsRepository.deleteById(id);

        List<Comment> comments = commentsRepository.findAllByTourId(id);
        return modelConverter.convertCommentsToDtoList(comments);
    }
}
