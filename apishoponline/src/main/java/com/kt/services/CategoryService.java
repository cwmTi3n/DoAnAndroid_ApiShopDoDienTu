package com.kt.services;

import com.kt.entities.Category;

import java.util.List;

public interface CategoryService {
    void deleteById(int id);
    Category findById(int id);
    <S extends Category> S save(S entity);
    List<Category> findAll();
}
