package com.ramseys.api_recommandation.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import com.ramseys.api_recommandation.dto.MediaDTO;
import com.ramseys.api_recommandation.dto.RecommandationDTO;
import com.ramseys.api_recommandation.model.*;
import com.ramseys.api_recommandation.repository.MovieRepository;
import com.ramseys.api_recommandation.repository.UserPreferenceRepository;
import com.ramseys.api_recommandation.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RecommendationService {
    private final UserPreferenceRepository userPreferenceRepository;
    private final MovieRepository movieRepository; // Assurez-vous d'avoir un repository pour les films
    public RecommendationService(UserPreferenceRepository userPreferenceRepository, MovieRepository movieRepository) {
        this.userPreferenceRepository = userPreferenceRepository;
        this.movieRepository = movieRepository;
    }
  public List<RecommandationDTO> getRecommendations(Long userId, int maxResults) {
    List<UserPreference> preferences = userPreferenceRepository.findByUserId(userId);
    List<Tag> tags = preferences.stream()
                                .map(UserPreference::getTag)
                                .collect(Collectors.toList());
    List<Movie> recommendedMovies = movieRepository.findAllByTagsIn(tags);
    // Ajoutez un log pour vérifier les films récupérés
    recommendedMovies.forEach(movie -> {
        System.out.println("Movie ID: " + movie.getId());
        System.out.println("Movie Title: " + movie.getTitle());
    });
    return recommendedMovies.stream()
                            .limit(maxResults)
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
}
    private RecommandationDTO convertToDTO(Movie movie) {
        RecommandationDTO dto = new RecommandationDTO();
        dto.setTitle(movie.getTitle());
        dto.setDirector(movie.getDirector());
        dto.setDuration(movie.getDuration());
        dto.setRating(movie.getRating());
        dto.setReleaseYear(movie.getReleaseYear());
        
        // Récupérer les noms des tags
        List<String> tagNames = movie.getTags().stream()
            .map(Tag::getName)
            .collect(Collectors.toList());
        dto.setGenres(tagNames); // Ou utilisez setTags() si vous avez une méthode pour les tags
        return dto;
    }
}