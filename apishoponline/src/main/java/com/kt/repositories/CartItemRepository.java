package com.kt.repositories;

import com.kt.entities.CartItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query("select c from CartItem c where c.cart.id = :cartId")
    List<CartItem> findAllByCartId(int cartId);
    @Query("select c from CartItem c where c.cart.id = :cartId and c.product.id = :productId")
    CartItem findByCartIdAndProductId(int cartId, int productId);

    @Query("select c from CartItem c where c.product.id = :productId and c.cart.status = :status")
    List<CartItem> findByCartStatusAndProductId(int status, int productId);

    @Query("select ci from CartItem ci where ci.product.user.id =:sellerId and ci.status =:status")
    Page<CartItem> findBySellerAndStatus(int sellerId, int status, Pageable pageable);

    @Query("select Count(*) from CartItem ci where ci.product.user.id =:sellerId and ci.status =:status")
    Integer totalOrder(int sellerId, int status);

    @Query("select Sum(ci.quantity * ci.unitPrice) from CartItem ci where ci.product.user.id =:sellerId and ci.status =:status")
    Float revenue(int sellerId, int status);
}
