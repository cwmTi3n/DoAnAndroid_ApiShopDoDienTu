package com.kt.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.entities.Product;

import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p where p.category.id = :categoryId")
    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
    Page<Product> findAll(Pageable pageable);
    @Query("select p from Product p where p.category.id = :categoryId and " +
            "(p.name like %:keyword% or p.description like %:keyword%)")
    Page<Product> searchAllByCategoryId(Integer categoryId, String keyword, Pageable pageable);
    @Query("select p from Product p where p.name like %:keyword% or p.description like %:keyword%")
    Page<Product> searchAll(String keyword, Pageable pageable);
    @Query("select p from Product p where p.user.id =:sellerId and (p.name like %:keyword% or p.description like %:keyword%)")
    Page<Product> searchProductBySeller(int sellerId, String keyword, Pageable pageable);
    @Query("select p from Product p where p.user.id =:sellerId and p.category.id =:categoryId  and (p.name like %:keyword% or p.description like %:keyword%)")
    Page<Product> searchProductBySellerAndCategoryId(int sellerId, int categoryId, String keyword, Pageable pageable);
}
