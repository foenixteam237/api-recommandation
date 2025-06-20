package com.ramseys.api_recommandation.model;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    
    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Media> media;
}