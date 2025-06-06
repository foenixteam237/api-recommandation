package com.ramseys.api_recommandation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ramseys.api_recommandation.model.Movie;
import com.ramseys.api_recommandation.model.Tag;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    
    @Query("SELECT DISTINCT m.director FROM Movie m")
    List<String> findAllDirectors();
    
    @Query("SELECT DISTINCT t.name FROM Tag t JOIN t.media m WHERE TYPE(m) = Movie AND t.category = 'genre'")
    List<String> findAllMovieGenres();

    @Query("SELECT m FROM Movie m JOIN m.tags t WHERE t IN :tags GROUP BY m.id")
    List<Movie> findAllByTagsIn(List<Tag> tags);
}