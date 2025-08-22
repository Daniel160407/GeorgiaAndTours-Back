package com.georgiaandtours.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    private Integer tourId;
    private String name;
    private String date;
    private Integer rating;
    private String payload;
}
