package com.kt.models;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopModel {
    private int userId;
    private int id;
    private MultipartFile imageFile;
    private String address;
    
}
