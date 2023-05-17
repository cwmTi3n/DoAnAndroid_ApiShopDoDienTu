package com.kt.services;

import java.util.List;

import com.kt.entities.Feedback;
import com.kt.entities.Product;

public interface FeedbackService {
    <S extends Feedback> S save(S entity);
    List<Feedback> findByProduct(Product product);
}
