package com.ramseys.api_recommandation.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDTO {
      private Long id;
    private String title;
    private int releaseYear;
    private String director;
    private int duration;
    private double rating;
    private Set<String> genres;
}
