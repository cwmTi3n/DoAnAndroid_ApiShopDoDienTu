package com.kt.utils;

import java.util.Random;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kt.entities.User;
import com.kt.models.CustomUserDetail;

public class Constant {
    public static final int pageSize = 6;
    public static final String placeholderAvatar = "https://res.cloudinary.com/dsaslc2qh/image/upload/v1682958284/avatar_auo1dk.png";
    public static User getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        return userDetails.getUser();
    }
    public static Sort getSort(String orderby) {
        Sort sort;
        if(orderby.contains("desc")) {
            orderby = orderby.substring(4, orderby.length());
            sort = Sort.by(orderby).descending();
        }
        else {
            sort = Sort.by(orderby);
        }
        return sort;
    }

    public static String getCode() {
        int codeLength = 6; // độ dài của mã xác nhận
        String chars = "0123456789"; // chỉ sử dụng các chữ số
        StringBuilder code = new StringBuilder(); // tạo một StringBuilder để lưu mã xác nhận
        // tạo một đối tượng Random để tạo số ngẫu nhiên
        Random random = new Random();
        // tạo mã xác nhận bằng cách chọn ngẫu nhiên các ký tự từ chuỗi chars
        for (int i = 0; i < codeLength; i++) {
            int index = random.nextInt(chars.length());
            code.append(chars.charAt(index));
        }
        return code.toString();
    }
}
