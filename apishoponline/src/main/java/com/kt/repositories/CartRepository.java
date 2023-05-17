package com.kt.repositories;

import com.kt.entities.Cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c where c.user.id = :userId and c.status = :status")
    Cart findByUserAndStatus(int userId, int status);

    @Query("select c from Cart c where c.user.id = :userId and c.status = :status")
    Page<Cart> findByUserAndStatus(int userId, int status, Pageable pageable);
}
