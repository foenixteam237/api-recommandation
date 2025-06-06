package com.ramseys.api_recommandation.dto;

import java.util.List;

import lombok.Data;

@Data
public class RecommandationDTO {
    private MediaDTO media;
    private int matchScore;
    private long id;
    private String title;
    private String director;
    private int duration;
    private Float rating;
    private List<String> genres;
    private int releaseYear;
}