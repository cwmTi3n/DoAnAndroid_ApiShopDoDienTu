package com.kt.controllers.web;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kt.dtos.ShopDto;
import com.kt.dtos.UserDto;
import com.kt.entities.Shop;
import com.kt.entities.User;
import com.kt.mapper.UserMapper;
import com.kt.models.ShopModel;
import com.kt.services.CloudinaryService;
import com.kt.services.ShopSercive;
import com.kt.services.UserService;
import com.kt.utils.Constant;
import com.kt.utils.Role;

@RestController
public class ShopController {
    @Autowired
    ShopSercive shopSercive;
    @Autowired
    UserService userService;
    @Autowired
    CloudinaryService cloudinaryService;
    @PostMapping("account/register-seller")
    public ResponseEntity<String> registerSeller(@Valid @ModelAttribute ShopModel shopModel) throws IOException {
            Shop shop = new Shop();
            BeanUtils.copyProperties(shopModel, shop);
            User user = userService.findById(Constant.getUserLogin().getId());
            String filename = cloudinaryService.uploadImage(shopModel.getImageFile());
            shop.setUser(userService.save(user));
            shop.setBanner(filename);
            if(shopSercive.sava(shop) == null) {
                return ResponseEntity.badRequest().body("Có lỗi xảy ra");
            }
            user.setShop(shop);
            user.setRole(Role.SELLER);
            userService.save(user);
            return ResponseEntity.ok().body("Đăng ký thành công");
    }

    @PutMapping("seller/update-banner")
    public ShopDto updateBanner(@RequestParam String address, @RequestParam MultipartFile imageFile) throws IOException {
        Shop shop = shopSercive.findById(Constant.getUserLogin().getShop().getId());
        cloudinaryService.deleteImage(shop.getBanner());
        String newImage = cloudinaryService.uploadImage(imageFile);
        shop.setBanner(newImage);
        shop.setAddress(address);
        shopSercive.sava(shop);
        ShopDto shopDto = new ShopDto(shop.getAddress(), shop.getBanner());
        return shopDto;
    }

    @GetMapping("shop-infor/{id}")
    public UserDto getShopInfor(@PathVariable int id) {
        User user = userService.findById(id);
        if(user != null) {
            return UserMapper.getInstance().toDto(user);
        }
        else {
            return null;
        }
    }
}
