package com.ramseys.api_recommandation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramseys.api_recommandation.model.UserPreference;

import java.util.List;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    List<UserPreference> findByUserId(Long userId);
}

