package com.ramseys.api_recommandation.dto;

import com.ramseys.api_recommandation.model.User;

public record UserResponse(
    Long id,
    String username,
    String email
) {
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail()
        );
    }
}