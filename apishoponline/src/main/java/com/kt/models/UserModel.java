package com.kt.models;

import com.kt.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private int id;
    private String username;
    private String email;
    private String fullname;
    private String password;
    private MultipartFile imageFile;
    private String phone;
    private int status;
    private Role role;
}
