package com.yuvi.passpal.repository;

import com.yuvi.passpal.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BotRepo extends JpaRepository<Password, Long> {
    @Query("select p from Password p where lower(p.name) like lower(concat('%', :name, '%'))")
    List<Password> findByNameContaining(String name);

    Optional<Password> findByNameIgnoreCase(String name);
}
