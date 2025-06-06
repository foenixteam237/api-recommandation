package com.ramseys.api_recommandation.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@DiscriminatorValue("MOVIE")
@EqualsAndHashCode(callSuper = true)
public class Movie extends Media {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String director;
    private int duration;
    private Float rating;
    private List<String> genres;
    @ManyToMany
    @JoinTable(name = "movie_tags",
               joinColumns = @JoinColumn(name = "movie_id"),
               inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
    public Float getRating() {
    
        if (rating != null) {
       return rating.floatValue();
   } else {
   
        return null;
    }
}

   
}