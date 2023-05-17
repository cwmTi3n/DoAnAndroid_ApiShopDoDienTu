package com.kt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.entities.Feedback;
import com.kt.entities.Product;
import com.kt.repositories.FeedbackRepository;
import com.kt.services.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService{
    @Autowired
    FeedbackRepository feedbackRepository;
    @Override
    public <S extends Feedback> S save(S entity) {
        return feedbackRepository.save(entity);
    }
    @Override
    public List<Feedback> findByProduct(Product product) {
        return feedbackRepository.findByProduct(product);
    }
    
}
