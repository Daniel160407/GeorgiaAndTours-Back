package com.georgiaandtours.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tour_id")
    private Integer tourId;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private String date;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "payload")
    private String payload;
}
