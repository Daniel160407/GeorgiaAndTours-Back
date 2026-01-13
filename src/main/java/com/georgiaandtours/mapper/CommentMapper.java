package com.georgiaandtours.mapper;

import com.georgiaandtours.dto.CommentDto;
import com.georgiaandtours.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommentMapper {
    Comment toEntity(CommentDto commentDto);

    List<CommentDto> toDtoList(List<Comment> comments);

    void updateCommentFromDto(@MappingTarget Comment comment, CommentDto commentDto);
}
