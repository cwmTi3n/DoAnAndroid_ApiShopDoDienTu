package com.kt.mapper;

import com.kt.dtos.ProductDto;
import com.kt.entities.Category;
import com.kt.entities.Product;
import com.kt.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {
    private static ProductMapper instance;
    private ProductMapper(){};
    public static ProductMapper getInstance() {
        if(instance == null) {
            instance = new ProductMapper();
        }
        return instance;
    }

    public ProductDto toDto(Product product) {
        if(product == null) {
            return null;
        }
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCode(product.getCode());
        productDto.setAmount(product.getAmount());
        productDto.setImage(product.getImage());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setStatus(product.getStatus());
        productDto.setDescription(product.getDescription());
        User user = product.getUser();
        if(user != null){
            productDto.setUserId(user.getId());
            productDto.setShopName(user.getUsername());
            productDto.setAvatarShop(user.getAvatar());
        }
        Category category = product.getCategory();
        if(category != null) {
            productDto.setCategoryId(product.getCategory().getId());
        }
        return productDto;
    }

    public List<ProductDto> toListDto(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product p : products) {
            productDtos.add(toDto(p));
        }
        return productDtos;
    }

}
