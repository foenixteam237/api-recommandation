package com.ramseys.api_recommandation.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramseys.api_recommandation.dto.MediaSearchRequest;
import com.ramseys.api_recommandation.dto.MovieDTO;
import com.ramseys.api_recommandation.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("search")
    public ResponseEntity<Page<MovieDTO>> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) Integer minDuration,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) Double minRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        // Validation des paramètres de pagination
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) { // Limite de taille pour éviter des requêtes trop grandes
            size = 10;
        }
        MediaSearchRequest request = new MediaSearchRequest();
        request.setTitle(title);
        request.setMinYear(minYear);
        request.setMaxYear(maxYear);
        request.setGenres(genres);
        request.setDirector(director);
        request.setMinDuration(minDuration);
        request.setMaxDuration(maxDuration);
        request.setMinRating(minRating);
        
        Sort sortObj = direction.equalsIgnoreCase("desc") 
            ? Sort.by(sort).descending() 
            : Sort.by(sort).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sortObj);
        
        return ResponseEntity.ok(movieService.searchMovies(request, pageable));
    }

    @GetMapping("/directors")
    public ResponseEntity<List<String>> getAllDirectors() {
        return ResponseEntity.ok(movieService.getAllDirectors());
    }

    @GetMapping("/genres")
    public ResponseEntity<List<String>> getAllGenres() {
        return ResponseEntity.ok(movieService.getAllGenres());
    }
}