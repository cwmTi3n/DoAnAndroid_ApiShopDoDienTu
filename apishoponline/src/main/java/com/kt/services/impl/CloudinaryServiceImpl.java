package com.kt.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kt.services.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;
    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        Map<String, String> options = new HashMap<>();
        options.put("public_id", UUID.randomUUID().toString());
        Map uploadResult = cloudinary.uploader()
                .upload(multipartFile.getBytes(), options);
        return (String) uploadResult.get("secure_url");
    }

    @Override
    public void deleteImage(String url) throws IOException {
        if(url == null) {
            return;
        }
        String publicId = url.replaceAll("^.*/([^/]+)\\.[^.]+$", "$1");
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
