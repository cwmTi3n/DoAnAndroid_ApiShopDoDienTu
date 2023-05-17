package com.kt.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private int id;
    private int quantity;
    private float unitPrice;
    private int status;
    private int productId;
    private int cartId;
    private String image;
    private String productName;
    private String shopName;
}
