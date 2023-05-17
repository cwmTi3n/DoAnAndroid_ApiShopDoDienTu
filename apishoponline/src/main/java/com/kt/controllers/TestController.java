package com.kt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kt.services.UserService;

@RestController
public class TestController {
    @RestController
    public class MyController {
        @Autowired
        UserService  userService;
        @GetMapping("/api/data")
        public String processData() {

            return userService.findById(5).getShop().getBanner();
        }
    }
}
