package com.georgiaandtours.util;

import com.georgiaandtours.dto.TourDto;
import com.georgiaandtours.model.Tour;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelConverter {
    public Tour convert(TourDto tourDto) {
        return Tour.builder()
                .id(tourDto.getId())
                .name(tourDto.getName())
                .description(tourDto.getDescription())
                .requirements(tourDto.getRequirements())
                .price(tourDto.getPrice())
                .duration(tourDto.getDuration())
                .direction(tourDto.getDirection())
                .imageUrl(tourDto.getImageUrl())
                .build();
    }

    public List<TourDto> convertToursToDtoList(List<Tour> tours) {
        List<TourDto> tourDtos = new ArrayList<>();
        tours.forEach(tour -> {
            tourDtos.add(
                    TourDto.builder()
                            .id(tour.getId())
                            .name(tour.getName())
                            .description(tour.getDescription())
                            .requirements(tour.getRequirements())
                            .price(tour.getPrice())
                            .duration(tour.getDuration())
                            .direction(tour.getDirection())
                            .imageUrl(tour.getImageUrl())
                            .build()
            );
        });
    }
}
