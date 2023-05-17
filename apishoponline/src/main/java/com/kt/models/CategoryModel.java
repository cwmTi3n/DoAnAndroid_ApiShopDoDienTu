package com.kt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {
    private int id;
    private String name;
    private MultipartFile imageFile;
    private int status;
}
