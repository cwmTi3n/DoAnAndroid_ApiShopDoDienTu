package com.kt.models;


import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackModel {
    private int id;
    private String content;
    private int star;
    private MultipartFile imageFile;
    private int productId;
    private int userId;
}
