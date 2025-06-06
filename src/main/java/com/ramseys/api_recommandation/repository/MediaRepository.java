package com.ramseys.api_recommandation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramseys.api_recommandation.model.Media;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query("SELECT DISTINCT m FROM Media m JOIN m.tags t WHERE t.id IN :tagIds")
    List<Media> findByTagIds(@Param("tagIds") List<Long> tagIds);
}