package com.kt.mapper;

import com.kt.dtos.CartDto;
import com.kt.entities.Cart;
import com.kt.entities.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {
    private static CartMapper instance;
    private CartMapper (){};
    public static CartMapper getInstance() {
        if(instance == null) {
            instance = new CartMapper();
        }
        return instance;
    }

    public CartDto toDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setStatus(cart.getStatus());
        cartDto.setId(cart.getId());
        cartDto.setUserId(cart.getUser().getId());
        cartDto.setBuyDate(cart.getBuyDate());
        cartDto.setAddress(cart.getAddress());
        List<CartItem> cartItems = cart.getCartItems();
        int sumProduct = 0;
        int sumPrice = 0;
        String avatar = null;
        for(CartItem ci : cartItems) {
            if(avatar == null) {
                avatar = ci.getProduct().getImage();
            }
            sumPrice += ci.getQuantity()*ci.getUnitPrice();
            sumProduct += ci.getQuantity();
        }
        cartDto.setAvatar(avatar);
        cartDto.setSumPrice(sumPrice);
        cartDto.setSumProduct(sumProduct);
        cartDto.setUsername(cart.getUser().getUsername());
        cartDto.setPhone(cart.getUser().getPhone());
        return cartDto;
    }

    public List<CartDto> toListDto(List<Cart> carts) {
        List<CartDto> cartDtos = new ArrayList<>();
        for(Cart c : carts) {
            cartDtos.add(toDto(c));
        }
        return cartDtos;
    }


}
