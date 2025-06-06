package com.ramseys.api_recommandation.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        // Filtrer par tags
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                // Ici, nous vérifions si le film a au moins un des tags spécifiés
                return root.join("tags").in(request.getTags());
            });
        }
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
        return movieRepository.findAll(pageable).map(this::convertToDTO);
    }

    public List<String> getAllDirectors() {
        return movieRepository.findAllDirectors();
    }

    public List<String> getAllGenres() {
        return movieRepository.findAllMovieGenres();
    }

    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO(null, null, 0, null, 0, 0, null);
        dto.setDirector(movie.getDirector());
        dto.setDuration(movie.getDuration());
        dto.setId(movie.getId());
        dto.setRating(movie.getRating());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setTitle(movie.getTitle());
        // Ici, nous récupérons les tags et les convertissons en une liste de chaînes
        if (movie.getTags() != null) {
            List<String> tagNames = movie.getTags().stream()
                    .map(Tag::getName) // Supposons que Tag a une méthode getName()
                    .collect(Collectors.toList());
            dto.setGenres(tagNames); // Ou utilisez setTags() si vous avez une méthode pour les tags
        }

        return dto;
    }
}