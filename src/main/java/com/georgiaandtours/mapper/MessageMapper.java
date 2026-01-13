package com.georgiaandtours.mapper;

import com.georgiaandtours.dto.MessageDto;
import com.georgiaandtours.model.Message;
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
public interface MessageMapper {
    Message toEntity(MessageDto messageDto);

    List<MessageDto> toDtoList(List<Message> messages);

    void updateMessageFromDto(@MappingTarget Message message, MessageDto messageDto);
}
