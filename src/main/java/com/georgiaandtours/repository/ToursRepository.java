package com.georgiaandtours.repository;

import com.georgiaandtours.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToursRepository extends JpaRepository<Tour, Integer> {
}
