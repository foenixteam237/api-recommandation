package com.ramseys.api_recommandation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ramseys.api_recommandation.dto.PreferenceRequest;
import com.ramseys.api_recommandation.dto.RecommandationDTO;
import com.ramseys.api_recommandation.model.Tag;
import com.ramseys.api_recommandation.model.User;
import com.ramseys.api_recommandation.model.UserPreference;
import com.ramseys.api_recommandation.repository.UserPreferenceRepository;
import com.ramseys.api_recommandation.repository.UserRepository;
import com.ramseys.api_recommandation.repository.TagRepository;
import com.ramseys.api_recommandation.service.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommandationController {
    private final RecommendationService recommendationService;
    private final UserPreferenceRepository userPreferenceRepository;
    private final UserRepository userRepository; // Ajouté
    private final TagRepository tagRepository; // Ajouté

    private static final int DEFAULT_MAX_RESULTS = 10;

    public RecommandationController(RecommendationService recommendationService,
            UserPreferenceRepository userPreferenceRepository,
            UserRepository userRepository, // Ajouté
            TagRepository tagRepository) { // Ajouté
        this.recommendationService = recommendationService;
        this.userPreferenceRepository = userPreferenceRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public ResponseEntity<List<RecommandationDTO>> getRecommendations(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "10") int maxResults) {
        if (maxResults <= 0) {
            maxResults = DEFAULT_MAX_RESULTS;
        }
        List<RecommandationDTO> recommendations = recommendationService.getRecommendations(userId, maxResults);
        return ResponseEntity.ok(new ArrayList<>(recommendations)); // Copie pour éviter ConcurrentModificationException
    }

    @PostMapping("/preferences")
    public ResponseEntity<UserPreference> addPreference(@Validated @RequestBody PreferenceRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        Tag tag = tagRepository.findById(request.getTagId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag not found"));
        UserPreference preference = new UserPreference();
        preference.setUser(user);
        preference.setTag(tag);
        preference.setWeight(request.getWeight());
        return ResponseEntity.ok(userPreferenceRepository.save(preference));
    }

}