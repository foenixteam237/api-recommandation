package com.ramseys.api_recommandation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramseys.api_recommandation.dto.BookDTO;
import com.ramseys.api_recommandation.dto.MediaSearchRequest;
import com.ramseys.api_recommandation.service.BookService;


@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
 

@GetMapping("search")
public ResponseEntity<Page<BookDTO>> searchBooks(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) Integer minYear,
        @RequestParam(required = false) Integer maxYear,
        @RequestParam(required = false) List<String> genres,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) Integer minPages,
        @RequestParam(required = false) Integer maxPages,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "title") String sort,
        @RequestParam(defaultValue = "asc") String direction) {
    
    MediaSearchRequest request = new MediaSearchRequest();
    request.setTitle(title);
    request.setMinYear(minYear);
    request.setMaxYear(maxYear);
    request.setGenres(genres);
    request.setAuthor(author);
    request.setMinPages(minPages);
    request.setMaxPages(maxPages);
    Sort sortObj = direction.equalsIgnoreCase("desc") 
        ? Sort.by(sort).descending() 
        : Sort.by(sort).ascending();
    
    Pageable pageable = PageRequest.of(page, size, sortObj);
    return ResponseEntity.ok(bookService.searchBooks(request, pageable));
}

 
    
    @GetMapping("/authors")
    public ResponseEntity<List<String>> getAllAuthors() {
        return ResponseEntity.ok(bookService.getAllAuthors());
    }

    @GetMapping("/genres")
    public ResponseEntity<List<String>> getAllGenres() {
        return ResponseEntity.ok(bookService.getAllGenres());
    }
}