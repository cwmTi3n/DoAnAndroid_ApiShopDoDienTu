package com.kt.services.impl;

import com.kt.dtos.PageDto;
import com.kt.entities.Product;
import com.kt.mapper.PageMapper;
import com.kt.repositories.ProductRepository;
import com.kt.services.ProductService;
import com.kt.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    PageMapper<Product> pageMapper = new PageMapper<>();
    @Override
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product findById(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(null);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public <S extends Product> S save(S entity) {
        return productRepository.save(entity);
    }

    @Override
    public PageDto<Product> findByCategoryId(Integer categoryId, String orderby, int page) {
        Pageable pageable = PageRequest.of(page, Constant.pageSize, Constant.getSort(orderby));
        Page<Product> pageProduct = productRepository.findByCategoryId(categoryId, pageable);
        return pageMapper.toPageDto(pageProduct);
    }

    @Override
    public PageDto<Product> findAll(String orderby, int page) {
        Pageable pageable = PageRequest.of(page, Constant.pageSize, Constant.getSort(orderby));
        Page<Product> productPage = productRepository.findAll(pageable);
        return pageMapper.toPageDto(productPage);
    }

    @Override
    public PageDto<Product> searchAllByCategoryId(Integer categoryId, String keyword, String orderby, int page) {
        Pageable pageable = PageRequest.of(page, Constant.pageSize, Constant.getSort(orderby));
        Page<Product> productPage = productRepository.searchAllByCategoryId(categoryId, keyword, pageable);
        return pageMapper.toPageDto(productPage);
    }

    @Override
    public PageDto<Product> searchAll(String keyword, String orderby, int page) {
        Pageable pageable = PageRequest.of(page, Constant.pageSize, Constant.getSort(orderby));
        Page<Product> productPage = productRepository.searchAll(keyword, pageable);
        return pageMapper.toPageDto(productPage);
    }


    @Override
    public PageDto<Product> searchProductBySeller(int sellerId, int categoryId, String keyword, String orderby,
            int page) {
        Pageable pageable = PageRequest.of(page, Constant.pageSize, Constant.getSort(orderby));
        if(categoryId == 0) {
            return pageMapper.toPageDto(productRepository.searchProductBySeller(sellerId, keyword, pageable));

        }
        else {
            return pageMapper.toPageDto(productRepository.searchProductBySellerAndCategoryId(sellerId, categoryId, keyword, pageable));
        }
    }

}
