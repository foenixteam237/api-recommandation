package com.ramseys.api_recommandation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ramseys.api_recommandation.dto.BookDTO;
import com.ramseys.api_recommandation.dto.MediaSearchRequest;
import com.ramseys.api_recommandation.model.Book;
import com.ramseys.api_recommandation.model.Media;
import com.ramseys.api_recommandation.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    @Autowired
    private final BookRepository bookRepository;

    public Page<BookDTO> searchBooks(MediaSearchRequest request, Pageable pageable) {
        Specification<Media> spec = Specification.where(
            MediaSpecifications.hasTitle(request.getTitle()))
            .and(MediaSpecifications.inYears(request.getMinYear(), request.getMaxYear()))
            .and(MediaSpecifications.hasGenres(request.getGenres()));
        
        if (request.getAuthor() != null) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("author")), "%" + request.getAuthor().toLowerCase() + "%"));
        }
        
        if (request.getMinPages() != null || request.getMaxPages() != null) {
            spec = spec.and((root, query, cb) -> 
                cb.between(root.get("pageCount"), 
                    request.getMinPages() != null ? request.getMinPages() : 0,
                    request.getMaxPages() != null ? request.getMaxPages() : Integer.MAX_VALUE));
        }
        
        return bookRepository.findAll(pageable).map(this::convertToDTO);
        //Page<Book> books = bookRepository.findAll(pageable);
        
    
    }
    
    public List<String> getAllAuthors() {
        return bookRepository.findAllAuthors();
    }
    
    public List<String> getAllGenres() {
        return bookRepository.findAllBookGenres();
    }
     private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO(null, null, 0, null, 0, null, null);
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setReleaseYear(book.getReleaseYear());
        dto.setPageCount(book.getPageCount());
        dto.setGenres(book.getGenres());
        return dto;
    }
}