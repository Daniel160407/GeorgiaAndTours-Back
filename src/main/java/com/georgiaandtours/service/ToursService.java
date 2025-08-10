package com.georgiaandtours.service;

import com.georgiaandtours.dto.TourDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ToursService {
    List<TourDto> getToursBy(String sorter);

    List<TourDto> addTour(TourDto tourDto);

    List<TourDto> editTour(TourDto tourDto);

    List<TourDto> deleteTour(Integer id);
}
