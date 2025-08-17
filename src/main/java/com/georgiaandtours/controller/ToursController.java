package com.georgiaandtours.controller;

import com.georgiaandtours.dto.TourDto;
import com.georgiaandtours.service.ToursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/georgiaandtours/tours")
@CrossOrigin(origins = "*")
public class ToursController {
    private final ToursService toursService;

    @Autowired
    public ToursController(ToursService toursService) {
        this.toursService = toursService;
    }

    @GetMapping
    public ResponseEntity<?> getTours(@RequestParam String sorter, @RequestParam String language) {
        List<TourDto> tourDtos = toursService.getToursBy(sorter, language);
        return ResponseEntity.ok(tourDtos);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchTours(@PathVariable String keyword) {
        List<TourDto> tourDtos = toursService.searchTours(keyword);
        return ResponseEntity.ok(tourDtos);
    }

    @PostMapping
    public ResponseEntity<?> addTour(@RequestBody TourDto tourDto) {
        List<TourDto> tourDtos = toursService.addTour(tourDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tourDtos);
    }

    @PutMapping
    public ResponseEntity<?> editTour(@RequestBody TourDto tourDto) {
        List<TourDto> tourDtos = toursService.editTour(tourDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tourDtos);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTour(@PathVariable Integer id) {
        List<TourDto> tourDtos = toursService.deleteTour(id);
        return ResponseEntity.ok(tourDtos);
    }
}
