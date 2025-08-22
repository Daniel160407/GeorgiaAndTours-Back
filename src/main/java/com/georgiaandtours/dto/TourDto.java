package com.georgiaandtours.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourDto {
    private Integer id;
    private String name;
    private String description;
    private String requirements;
    private String price;
    private String duration;
    private String direction;
    private String language;
    private String badge;
    private String imageUrl;
}
