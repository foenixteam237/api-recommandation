package com.ramseys.api_recommandation.model;
import java.util.Set;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@DiscriminatorValue("BOOK")
@EqualsAndHashCode(callSuper = true)
public class Book extends Media {
    private String author;
    private int pageCount;
    private String publisher;
    private Set<String> genres;
    public String getPublisher() {
        return publisher;
    }
}