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
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "requirements")
    private String requirements;
    @Column(name = "price")
    private String price;
    @Column(name = "duration")
    private String duration;
    @Column(name = "direction")
    private String direction;
    @Column(name = "language")
    private String language;
    @Column(name = "image_url")
    private String imageUrl;
}
