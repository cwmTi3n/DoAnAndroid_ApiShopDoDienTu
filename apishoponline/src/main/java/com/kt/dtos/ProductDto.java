package com.kt.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private int id;
    private String name;
    private int code;
    private String description;
    private float price;
    private int amount;
    private int stock;
    private String image;
    private int status;
    private int categoryId;
    private int userId;
    private String shopName;
    private String avatarShop;
}
