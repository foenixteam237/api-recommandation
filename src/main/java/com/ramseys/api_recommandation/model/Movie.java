package com.ramseys.api_recommandation.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@DiscriminatorValue("MOVIE")
@EqualsAndHashCode(callSuper = true)
public class Movie extends Media {
    private String director;
    private int duration;
    private Float rating;
    public Float getRating() {
    
        if (rating != null) {
       return rating.floatValue();
   } else {
   
        return null;
    }
}
}