package com.kt.services;

import com.kt.entities.User;

import java.util.List;

public interface UserService {
    void deleteById(int id);
    User findByUsername(String username);
    User findById(int id);
    List<User> findAll();
    <S extends User> S save(S entity);
}
