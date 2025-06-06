package com.ramseys.api_recommandation.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import com.ramseys.api_recommandation.dto.MediaSearchRequest;
import com.ramseys.api_recommandation.dto.MovieDTO;
import com.ramseys.api_recommandation.model.Media;
import com.ramseys.api_recommandation.model.Movie;
import com.ramseys.api_recommandation.model.Tag;
import com.ramseys.api_recommandation.repository.MovieRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    public Page<MovieDTO> searchMovies(MediaSearchRequest request, Pageable pageable) {
    Specification<Media> spec = Specification.where(
        MediaSpecifications.hasTitle(request.getTitle()))
        .and(MediaSpecifications.inYears(request.getMinYear(), request.getMaxYear()))
        .and(MediaSpecifications.hasGenres(request.getGenres()));
    if (request.getDirector() != null) {
        String director = request.getDirector().toLowerCase();
        spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("director")), "%" + director + "%"));
    }
    if (request.getMinDuration() != null || request.getMaxDuration() != null) {
        Integer minDuration = request.getMinDuration() != null ? request.getMinDuration() : 0;
        Integer maxDuration = request.getMaxDuration() != null ? request.getMaxDuration() : Integer.MAX_VALUE;
        spec = spec.and((root, query, cb) -> cb.between(root.get("duration"), minDuration, maxDuration));
    }
    if (request.getMinRating() != null) {
        spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("rating"), request.getMinRating()));
    }
    // Appliquer la spécification dans la recherche
    Page<Movie> movies = movieRepository.findAll( pageable);
  return movies.map(movie -> {
    Set<String> genreTags = new HashSet<>();
    
    // Utilisation d'une boucle pour éviter ConcurrentModificationException
    for (Tag tag : movie.getTags()) {
        if ("genre".equals(tag.getCategory())) {
            genreTags.add(tag.getName());
        }
    }
    for (Tag tag : movie.getTags()) {
    System.out.println("Traitement du tag : " + tag.getName());
    if ("genre".equals(tag.getCategory())) {
        genreTags.add(tag.getName());
    }
}
    return new MovieDTO(
        movie.getId(),
        movie.getTitle(),
        movie.getReleaseYear(),
        movie.getDirector(),
        movie.getDuration(),
        movie.getRating() != null ? movie.getRating().floatValue() : 0,
        genreTags // Utilisation de la collection temporaire
    );
});
}
    public List<String> getAllDirectors() {
        return movieRepository.findAllDirectors();
    }
    
    public List<String> getAllGenres() {
        return movieRepository.findAllMovieGenres();
    }
}