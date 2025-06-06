package com.ramseys.api_recommandation.dto;

import lombok.Data;
import java.util.Set;

@Data
public class MediaDTO {
    private Long id;
    private String title;
    private int releaseYear;
    private String type;
    private Set<String> tags;
    private String director;
    private Integer duration;
    private String author;
    private Integer pageCount;
}