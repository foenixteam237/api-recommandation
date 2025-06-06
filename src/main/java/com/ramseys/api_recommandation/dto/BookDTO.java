package com.ramseys.api_recommandation.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private int releaseYear;
    private String author;
    private int pageCount;
    private String publisher;
    private Set<String> genres;
}