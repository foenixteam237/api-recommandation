package com.ramseys.api_recommandation.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ramseys.api_recommandation.model.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByCategory(String category);
}