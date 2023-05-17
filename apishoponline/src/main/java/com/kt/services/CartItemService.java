package com.kt.services;

import com.kt.dtos.PageDto;
import com.kt.entities.CartItem;

import java.util.List;


public interface CartItemService {
    <S extends CartItem> S save(S entity);
    CartItem findById(int id);
    void deleteById(int id);
    List<CartItem> findByCartId(int id);
    CartItem findByCartIdAndProductId(int cartId, int productId);
    List<CartItem> findCartItemNoCheckOutByProduct(int productId);
    PageDto<CartItem> findBySellerAndStatus(int sellerId, int status, int page);
    int totalOrder(int sellerId, int status);
    float revenue(int sellerId, int status);
    void deliveredCartItem();
}
