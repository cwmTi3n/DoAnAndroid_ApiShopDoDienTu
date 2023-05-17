package com.kt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    private int id;
    private String name;
    private int code;
    private String description;
    private float price;
    private int amount;
    private int stock;
    private MultipartFile imageFile;
    private int status;
    private int categoryId;
    private int userId;
}
