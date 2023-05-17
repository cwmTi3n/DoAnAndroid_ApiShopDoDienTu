package com.kt.services.impl;

import com.kt.dtos.PageDto;
import com.kt.entities.CartItem;
import com.kt.mapper.PageMapper;
import com.kt.repositories.CartItemRepository;
import com.kt.services.CartItemService;
import com.kt.utils.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;
    PageMapper<CartItem> pageMapper = new PageMapper<>();
    @Override
    public <S extends CartItem> S save(S entity) {
        return cartItemRepository.save(entity);
    }

    @Override
    public CartItem findById(int id) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);
        return cartItemOptional.orElse(null);
    }

    @Override
    public void deleteById(int id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public List<CartItem> findByCartId(int id) {
        return cartItemRepository.findAllByCartId(id);
    }

    @Override
    public CartItem findByCartIdAndProductId(int cartId, int productId) {
        return cartItemRepository.findByCartIdAndProductId(cartId, productId);
    }

    @Override
    public List<CartItem> findCartItemNoCheckOutByProduct(int productId) {
        return cartItemRepository.findByCartStatusAndProductId(0, productId);
    }

    @Override
    public PageDto<CartItem> findBySellerAndStatus(int sellerId, int status, int page) {
        Pageable pageable = PageRequest.of(page, Constant.pageSize);
        return pageMapper.toPageDto(cartItemRepository.findBySellerAndStatus(sellerId, status, pageable));
    }

    @Override
    public int totalOrder(int sellerId, int status) {
        Integer result = cartItemRepository.totalOrder(sellerId, status);
        if(result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public float revenue(int sellerId, int status) {
        Float result = cartItemRepository.revenue(sellerId, status);
        if(result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public void deliveredCartItem() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        for(CartItem ci : cartItems) {
            if(ci.getStatus() == 2) {
                long currentTimeMillis = new Date().getTime();
                long sevenDaysInMillis = 7 * 24 * 60 * 60 * 1000; 
                if(currentTimeMillis - ci.getCart().getBuyDate().getTime() > sevenDaysInMillis) {
                    cartItemRepository.delete(ci);
                }
            }
        }
    }
}
