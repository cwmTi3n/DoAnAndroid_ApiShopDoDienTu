package com.kt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
