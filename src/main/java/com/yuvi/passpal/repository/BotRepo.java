package com.yuvi.passpal.repository;

import com.yuvi.passpal.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotRepo extends JpaRepository<Password, Long> {
    Optional<Password> findByName(String name);
}
