package com.kt.mapper;

import com.kt.dtos.UserDto;
import com.kt.entities.User;
import com.kt.utils.Constant;
import com.kt.utils.Role;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    private static UserMapper instance;
    private UserMapper(){};
    public static UserMapper getInstance() {
        if(instance == null) {
            instance = new UserMapper();
        }
        return instance;
    }

    public UserDto toDto(User user) {
        if(user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        if(user.getAvatar() == null) {
            userDto.setAvatar(Constant.placeholderAvatar);
        }
        else {
            userDto.setAvatar(user.getAvatar());
        }
        userDto.setStatus(user.getStatus());
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setRole(user.getRole());
        userDto.setFullname(user.getFullname());
        userDto.setUsername(user.getUsername());
        if(user.getRole() == Role.SELLER) {
            userDto.setBannerShop(user.getShop().getBanner());
        }
        return userDto;
    }

    public List<UserDto> toListDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for(User u : users) {
            userDtos.add(toDto(u));
        }
        return userDtos;
    }

}
