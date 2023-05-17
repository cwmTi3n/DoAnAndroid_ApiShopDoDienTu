package com.kt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.entities.Feedback;
import com.kt.entities.Product;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer>{
    List<Feedback> findByProduct(Product product);
}
