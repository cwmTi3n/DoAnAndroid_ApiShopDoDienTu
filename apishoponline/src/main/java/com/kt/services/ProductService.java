package com.kt.services;

import com.kt.dtos.PageDto;
import com.kt.entities.Product;

import java.util.List;

public interface ProductService {
    void deleteById(int id);
    Product findById(int id);
    List<Product> findAll();
    <S extends Product> S save(S entity);
    PageDto<Product> findByCategoryId(Integer categoryId, String orderby, int page);
    PageDto<Product> findAll(String orderby, int page);
    PageDto<Product> searchAllByCategoryId(Integer categoryId, String keyword, String orderby, int page);
    PageDto<Product> searchAll(String keyword, String orderby, int page);
    PageDto<Product> searchProductBySeller(int sellerId, int categoryId, String keyword, String orderby, int page);
}
