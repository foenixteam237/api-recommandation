package com.ramseys.api_recommandation.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import com.ramseys.api_recommandation.dto.MediaDTO;
import com.ramseys.api_recommandation.dto.RecommandationDTO;
import com.ramseys.api_recommandation.model.*;
import com.ramseys.api_recommandation.repository.UserPreferenceRepository;

@Service
public class RecommendationService {
    private final MediaService mediaService;
    private final UserPreferenceRepository userPreferenceRepository;

    public RecommendationService(MediaService mediaService, 
                               UserPreferenceRepository userPreferenceRepository) {
        this.mediaService = mediaService;
        this.userPreferenceRepository = userPreferenceRepository;
    }

    public List<RecommandationDTO> getRecommendations(Long userId, int maxResults) {
        List<UserPreference> preferences = userPreferenceRepository.findByUserId(userId);
        if (preferences.isEmpty()) return Collections.emptyList();

        List<Long> tagIds = preferences.stream()
                .map(pref -> pref.getTag().getId())
                .collect(Collectors.toList());

        List<Media> relevantMedia = mediaService.getMediaByTags(tagIds);

        Map<Long, Integer> mediaScores = new HashMap<>();
        Map<Long, Media> mediaMap = new HashMap<>();

        for (Media media : relevantMedia) {
            int score = 0;
            for (Tag tag : media.getTags()) {
                for (UserPreference pref : preferences) {
                    if (pref.getTag().getId().equals(tag.getId())) {
                        score += pref.getWeight();
                    }
                }
            }
            mediaScores.put(media.getId(), score);
            mediaMap.put(media.getId(), media);
        }

        return mediaScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .limit(maxResults)
                .map(entry -> {
                    Media media = mediaMap.get(entry.getKey());
                    return new RecommandationDTO();
                })
                .collect(Collectors.toList());
    }

    private MediaDTO convertToDTO(Media media) {
        MediaDTO dto = new MediaDTO();
        dto.setId(media.getId());
        dto.setTitle(media.getTitle());
        dto.setReleaseYear(media.getReleaseYear());
        
        Set<String> tagNames = media.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
        dto.setTags(tagNames);

        if (media instanceof Movie) {
            Movie movie = (Movie) media;
            dto.setType("MOVIE");
            dto.setDirector(movie.getDirector());
            dto.setDuration(movie.getDuration());
        } else if (media instanceof Book) {
            Book book = (Book) media;
            dto.setType("BOOK");
            dto.setAuthor(book.getAuthor());
            dto.setPageCount(book.getPageCount());
        }
        
        return dto;
    }
}