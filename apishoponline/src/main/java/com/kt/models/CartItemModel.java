package com.kt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemModel {
    private int id;
    private int quantity;
    private float unitPrice;
    private int status;
    private int productId;
    private int cartId;
    private int userId;
}
