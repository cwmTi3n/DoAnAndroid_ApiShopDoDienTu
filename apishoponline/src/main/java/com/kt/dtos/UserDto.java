package com.kt.dtos;

import com.kt.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    private String username;
    private String email;
    private String fullname;
    private String avatar;
    private String phone;
    private int status;
    private Role role;
    private String bannerShop;
}
