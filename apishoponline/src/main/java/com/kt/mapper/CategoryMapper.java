package com.kt.mapper;

import com.kt.dtos.CategoryDto;
import com.kt.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    private static CategoryMapper instance;
    private CategoryMapper(){};
    public static CategoryMapper getInstance() {
        if(instance == null) {
            instance = new CategoryMapper();
        }
        return instance;
    }

    public CategoryDto toDto(Category category) {
        if(category == null) {
            return null;
        }
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setStatus(category.getStatus());
        categoryDto.setImage(category.getImage());
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public List<CategoryDto> toListDto(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for(Category c : categories) {
            categoryDtos.add(toDto(c));
        }
        return categoryDtos;
    }
}
