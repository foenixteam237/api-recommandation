package com.ramseys.api_recommandation.dto;

import java.util.List;

import lombok.Data;

@Data
public class MediaSearchRequest {
    private String title;
    private Integer minYear;
    private Integer maxYear;
    private List<String> genres;
    private String director;
    private String author;
    private Integer minDuration;
    private Integer maxDuration;
    private Integer minPages;
    private Integer maxPages;
    private Double minRating;
    private String sortBy; // "title", "releaseYear", "rating", etc.
    private String sortDirection = "asc"; // "asc" or "desc"
}