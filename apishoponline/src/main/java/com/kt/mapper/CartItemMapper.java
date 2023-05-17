package com.kt.mapper;

import com.kt.dtos.CartItemDto;
import com.kt.entities.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartItemMapper {
    private static CartItemMapper instance;
    private CartItemMapper(){};
    public static CartItemMapper getInstance() {
        if(instance == null) {
            instance = new CartItemMapper();
        }
        return instance;
    }

    public CartItemDto toDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItem.getId());
        cartItemDto.setCartId(cartItem.getCart().getId());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setStatus(cartItem.getStatus());
        cartItemDto.setUnitPrice(cartItem.getUnitPrice());
        cartItemDto.setProductId(cartItem.getProduct().getId());
        cartItemDto.setImage(cartItem.getProduct().getImage());
        cartItemDto.setProductName(cartItem.getProduct().getName());
        cartItemDto.setShopName(cartItem.getProduct().getUser().getUsername());
        return cartItemDto;
    }

    public List<CartItemDto> toListDto(List<CartItem> carItems) {
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for(CartItem cartItem : carItems) {
            cartItemDtos.add(toDto(cartItem));
        }
        return cartItemDtos;
    }

}
