package com.georgiaandtours.service;

import com.georgiaandtours.dto.TourDto;
import com.georgiaandtours.model.Tour;
import com.georgiaandtours.repository.ToursRepository;
import com.georgiaandtours.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToursServiceImpl implements ToursService {
    private final ToursRepository toursRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public ToursServiceImpl(ToursRepository toursRepository, ModelConverter modelConverter) {
        this.toursRepository = toursRepository;
        this.modelConverter = modelConverter;
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

        return modelConverter.convertToursToDtoList(tours);
    }

    @Override
    public List<TourDto> searchTours(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return modelConverter.convertToursToDtoList(toursRepository.findAll());
        }

        List<Tour> tours = toursRepository.findAll();
        List<Tour> filteredTours = tours.stream()
                .filter(tour ->
                        tour.getName() != null && tour.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                                tour.getDescription() != null && tour.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                                tour.getRequirements() != null && tour.getRequirements().toLowerCase().contains(keyword.toLowerCase())
                )
                .collect(Collectors.toList());

        return modelConverter.convertToursToDtoList(filteredTours);
    }

    @Override
    public List<TourDto> addTour(TourDto tourDto) {
        Tour convertedTour = modelConverter.convert(tourDto);
        toursRepository.save(convertedTour);

        List<Tour> tours = toursRepository.findAll();
        return modelConverter.convertToursToDtoList(tours);
    }

    @Override
    public List<TourDto> editTour(TourDto tourDto) {
        Optional<Tour> tourOptional = toursRepository.findById(tourDto.getId());
        tourOptional.ifPresent(tour -> {
            tour.setName(tourDto.getName());
            tour.setDescription(tourDto.getDescription());
            tour.setRequirements(tourDto.getRequirements());
            tour.setPrice(tourDto.getPrice());
            tour.setDuration(tourDto.getDuration());
            tour.setDirection(tourDto.getDirection());
            tour.setImageUrl(tourDto.getImageUrl());

            toursRepository.save(tour);
        });

        List<Tour> tours = toursRepository.findAll();
        return modelConverter.convertToursToDtoList(tours);
    }

    @Override
    public List<TourDto> deleteTour(Integer id) {
        toursRepository.deleteById(id);

        List<Tour> tours = toursRepository.findAll();
        return modelConverter.convertToursToDtoList(tours);
    }
}