package com.kt.controllers.web;

import com.kt.dtos.UserDto;
import com.kt.entities.User;
import com.kt.mapper.UserMapper;
import com.kt.models.UserModel;
import com.kt.services.CloudinaryService;
import com.kt.services.ImageService;
import com.kt.services.UserService;
import com.kt.utils.Constant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    UserService userService;
    @Autowired
    ImageService imageService;
    @Autowired
    CloudinaryService cloudinaryService;
    @PostMapping("")
    public UserDto createUser(@Valid @ModelAttribute("user")UserModel userModel) throws IOException {
        User user = new User();
        BeanUtils.copyProperties(userModel, user);
        MultipartFile file = userModel.getImageFile();
        if(file != null) {
            if(!file.isEmpty()) {
//                String filename = imageService.save(file);
                String filename = cloudinaryService.uploadImage(file);
                user.setAvatar(filename);
            }
        }
        else {
            user.setAvatar(Constant.placeholderAvatar);
        }
        return UserMapper.getInstance().toDto(userService.save(user));
    }
    @GetMapping("")
    public List<UserDto> getAllUser() {

        return UserMapper.getInstance().toListDto(userService.findAll());
    }

    @GetMapping("{id}")
    public UserDto getUser(@PathVariable int id) {

        return UserMapper.getInstance().toDto(userService.findById(id));
    }

    @PutMapping("/update-name")
    public UserDto updateUser(@RequestParam int id, @RequestParam String fullname) throws IOException {
        User user = userService.findById(id);
        if(user != null) {
            user.setFullname(fullname);
        }
        return UserMapper.getInstance().toDto(userService.save(user));
    }

    @PutMapping("/update-avatar")
    public UserDto updateAvatar(@RequestParam MultipartFile imageFile) throws IOException {
        User user = userService.findById(Constant.getUserLogin().getId());
        String newImage = cloudinaryService.uploadImage(imageFile);
        if(user.getAvatar() != null) {
            cloudinaryService.deleteImage(user.getAvatar());
        }
        user.setAvatar(newImage);
        return UserMapper.getInstance().toDto(userService.save(user));
    }

    @PutMapping("/update-pw")
    public UserDto updatePw(@RequestParam int id,
                            @RequestParam String oldpw,
                            @RequestParam String newpw) {
        User user = userService.findById(id);
        if(user != null) {
            if(user.getPassword().equals(oldpw)) {
                user.setPassword(newpw);
            }
        }
        return UserMapper.getInstance().toDto(userService.save(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) throws IOException {
        if(id == null) {
            return new ResponseEntity<>("Xóa không thành công", HttpStatus.BAD_REQUEST);
        }
        User user = userService.findById(id);
        if(user == null) {
            return new ResponseEntity<>("Xóa không thành công", HttpStatus.BAD_REQUEST);
        }
        else {
//            imageService.delete(user.getImages());
            cloudinaryService.deleteImage(user.getAvatar());
            userService.deleteById(id);
            return new ResponseEntity<>("Xóa thành công user có id: " + id.toString(), HttpStatus.OK);
        }
    }
}
