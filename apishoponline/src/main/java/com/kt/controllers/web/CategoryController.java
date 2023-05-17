package com.kt.controllers.web;

import com.kt.entities.Category;
import com.kt.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("")
    public List<Category> getAllCategory() {
        return categoryService.findAll();
    }
    @GetMapping("{id}")
    public Category getCategory(@PathVariable int id) {
        return categoryService.findById(id);
    }

}
