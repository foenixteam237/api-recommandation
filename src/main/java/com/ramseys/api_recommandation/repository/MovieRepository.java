package com.ramseys.api_recommandation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ramseys.api_recommandation.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    
    @Query("SELECT DISTINCT m.director FROM Movie m")
    List<String> findAllDirectors();
    
    @Query("SELECT DISTINCT t.name FROM Tag t JOIN t.media m WHERE TYPE(m) = Movie AND t.category = 'genre'")
    List<String> findAllMovieGenres();
}