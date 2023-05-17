package com.kt.services.impl;

import com.kt.entities.Cart;
import com.kt.repositories.CartRepository;
import com.kt.services.CartService;
import com.kt.utils.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Override
    public <S extends Cart> S save(S entity) {
        return cartRepository.save(entity);
    }

    @Override
    public Cart findById(int id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        return cartOptional.orElse(null);
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart findCartNoCheckOut(int userId) {
        return cartRepository.findByUserAndStatus(userId, 0);
    }

    @Override
    public Page<Cart> findByUserAndStatus(int userId, int status, int page) {
        Pageable pageable = PageRequest.of(page, Constant.pageSize, Constant.getSort("buyDate"));

        return cartRepository.findByUserAndStatus(userId, status, pageable);
    }
}
