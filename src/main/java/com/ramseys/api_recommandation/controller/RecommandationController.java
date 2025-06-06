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
import com.ramseys.api_recommandation.service.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController
@RequestMapping("/api/recommendations")
public class RecommandationController {
    private final RecommendationService recommendationService;
    private final UserPreferenceRepository userPreferenceRepository;
    
    private static final int DEFAULT_MAX_RESULTS = 10;
    public RecommandationController(RecommendationService recommendationService,
                                    UserPreferenceRepository userPreferenceRepository) {
        this.recommendationService = recommendationService;
        this.userPreferenceRepository = userPreferenceRepository;
    }
    @GetMapping
    public ResponseEntity<List<RecommandationDTO>> getRecommendations(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "10") int maxResults) {
        
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must not be null");
        }
        if (maxResults <= 0) {
            maxResults = DEFAULT_MAX_RESULTS; // Ensure positive maxResults
        }
        
        return ResponseEntity.ok(
            recommendationService.getRecommendations(userId, maxResults)
        );
    }
    @PostMapping("/preferences")
    public ResponseEntity<UserPreference> addPreference(@Validated @RequestBody PreferenceRequest request) {
        UserPreference preference = new UserPreference();
        
        // Assuming request contains userId and tagId to populate User and Tag
        User user = new User();
        user.setId(request.getUserId()); // Assuming PreferenceRequest has a getUserId() method
        preference.setUser(user);
        
        Tag tag = new Tag();
        tag.setId(request.getTagId()); // Assuming PreferenceRequest has a getTagId() method
        preference.setTag(tag);
        
        preference.setWeight(request.getWeight());
        
        return ResponseEntity.ok(userPreferenceRepository.save(preference));
    }
}