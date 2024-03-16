package com.sql.authentication.repository;

import com.sql.authentication.model.Ecategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EcategoryRepository extends JpaRepository<Ecategory,Integer> {
    boolean existsByName(String name);
    Optional<Ecategory> findByName(String name);
    List<Ecategory> findAllByStatus(int i);
}
