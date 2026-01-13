package com.georgiaandtours.service;

import com.georgiaandtours.dto.TourDto;
import com.georgiaandtours.mapper.TourMapper;
import com.georgiaandtours.model.Tour;
import com.georgiaandtours.repository.ToursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToursServiceImpl implements ToursService {
    private final ToursRepository toursRepository;
    private final TourMapper tourMapper;

    @Autowired
    public ToursServiceImpl(ToursRepository toursRepository, TourMapper tourMapper) {
        this.toursRepository = toursRepository;
        this.tourMapper = tourMapper;
    }

    @Override
    public List<TourDto> getToursBy(String sorter, String language) {
        List<Tour> tours = toursRepository.findAllByLanguage(language);
        switch (sorter) {
            case "name":
                tours.sort(Comparator.comparing(Tour::getName));
                break;
            case "price":
                tours.sort(Comparator.comparingDouble(tour -> Double.parseDouble(tour.getPrice())));
                break;
        }

        return tourMapper.toDtoList(tours);
    }

    @Override
    public List<TourDto> searchTours(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return tourMapper.toDtoList(toursRepository.findAll());
        }

        List<Tour> tours = toursRepository.findAll();
        List<Tour> filteredTours = tours.stream()
                .filter(tour ->
                        tour.getName() != null && tour.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                                tour.getDescription() != null && tour.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                                tour.getRequirements() != null && tour.getRequirements().toLowerCase().contains(keyword.toLowerCase())
                )
                .collect(Collectors.toList());

        return tourMapper.toDtoList(filteredTours);
    }

    @Override
    public List<TourDto> addTour(TourDto tourDto) {
        Tour convertedTour = tourMapper.toEntity(tourDto);
        toursRepository.save(convertedTour);

        List<Tour> tours = toursRepository.findAll();
        return tourMapper.toDtoList(tours);
    }

    @Override
    public List<TourDto> editTour(TourDto tourDto) {
        Optional<Tour> tourOptional = toursRepository.findById(tourDto.getId());
        tourOptional.ifPresent(tour -> {
            tourMapper.updateTourFromDto(tour, tourDto);
            toursRepository.save(tour);
        });

        List<Tour> tours = toursRepository.findAll();
        return tourMapper.toDtoList(tours);
    }

    @Override
    public List<TourDto> deleteTour(Integer id) {
        toursRepository.deleteById(id);

        List<Tour> tours = toursRepository.findAll();
        return tourMapper.toDtoList(tours);
    }
}