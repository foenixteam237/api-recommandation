package com.ramseys.api_recommandation.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ramseys.api_recommandation.model.Media;
import com.ramseys.api_recommandation.model.Tag;

import jakarta.persistence.criteria.Join;

public class MediaSpecifications {
    
    public static Specification<Media> hasTitle(String title) {
        return (root, query, cb) -> 
            title == null ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }
    
    public static Specification<Media> inYears(Integer minYear, Integer maxYear) {
        return (root, query, cb) -> {
            if (minYear == null && maxYear == null) return null;
            if (minYear == null) return cb.lessThanOrEqualTo(root.get("releaseYear"), maxYear);
            if (maxYear == null) return cb.greaterThanOrEqualTo(root.get("releaseYear"), minYear);
            return cb.between(root.get("releaseYear"), minYear, maxYear);
        };
    }
    
    public static Specification<Media> hasGenres(List<String> genres) {
        return (root, query, cb) -> {
            if (genres == null || genres.isEmpty()) return null;
            
            Join<Media, Tag> tagJoin = root.join("tags");
            return tagJoin.get("name").in(genres);
        };
    }
    
    // Ajoutez d'autres spécifications selon les besoins
}