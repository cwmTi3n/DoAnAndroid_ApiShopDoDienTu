package com.kt.controllers.web;

import com.kt.dtos.UserDto;
import com.kt.entities.User;
import com.kt.mapper.UserMapper;
import com.kt.models.CustomUserDetail;
import com.kt.services.EmailService;
import com.kt.services.UserService;
import com.kt.utils.Constant;
import com.kt.utils.Role;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;
    @Autowired
    DaoAuthenticationProvider authenticationProvider;
    private Map<String, String> otpMap = new HashMap<>();
    @PostMapping("signup")
    public UserDto signup(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String email,
                          @RequestParam String phone) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        user.setPhone(phone);
        user.setRole(Role.USER);
        user.setStatus(1);
        return  UserMapper.getInstance().toDto(userService.save(user));
    }
    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username,
                    password));
            CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
            return  ResponseEntity.ok(UserMapper.getInstance().toDto(customUserDetail.getUser()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("forgot-password/get-otp")
    public ResponseEntity<?> getOtp(@RequestParam(defaultValue = "") String username) {
        User user = userService.findByUsername(username);
        if(user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        String code = Constant.getCode();
        emailService.sendMail(user.getEmail(), "Mã code thay đổi mật khẩu", code);
        otpMap.put(username, code);
        return ResponseEntity.ok().body("Mã được gửi");
    }

    @PostMapping("forget-password/change-pass")
    public UserDto changePass(@RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String password, @RequestParam(defaultValue = "") String code) {
        String cfCode = otpMap.get(username);
        if(cfCode == null) {
            return null;
        }
        if(cfCode.equals(code)) {
            User user = userService.findByUsername(username);
            if(user != null) {
                otpMap.remove(username);
                user.setPassword(password);
                return UserMapper.getInstance().toDto(userService.save(user));
            }
        }
        return null;
    }


}
