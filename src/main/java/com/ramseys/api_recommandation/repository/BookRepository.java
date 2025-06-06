package com.ramseys.api_recommandation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ramseys.api_recommandation.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    
    @Query("SELECT DISTINCT b.author FROM Book b")
    List<String> findAllAuthors();
    
    @Query("SELECT DISTINCT t.name FROM Tag t JOIN t.media m WHERE TYPE(m) = Book AND t.category = 'genre'")
    List<String> findAllBookGenres();
}