package com.georgiaandtours.mapper;

import com.georgiaandtours.dto.TourDto;
import com.georgiaandtours.model.Tour;
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
public interface TourMapper {
    Tour toEntity(TourDto tourDto);

    List<TourDto> toDtoList(List<Tour> tours);

    void updateTourFromDto(@MappingTarget Tour tour, TourDto tourDto);
}
