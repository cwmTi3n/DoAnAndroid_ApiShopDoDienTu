package com.kt.services;

import com.kt.entities.Cart;

import java.util.List;

import org.springframework.data.domain.Page;

public interface CartService {
    <S extends Cart> S save(S entity);
    Cart findById(int id);
    List<Cart> findAll();
    void deleteById(int id);
    Cart findCartNoCheckOut(int userId);
    Page<Cart> findByUserAndStatus(int userId, int status, int page);
}
