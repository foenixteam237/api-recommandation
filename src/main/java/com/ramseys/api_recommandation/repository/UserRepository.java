package com.ramseys.api_recommandation.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramseys.api_recommandation.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    static Optional<User> findByUsername(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
    }
}