package com.kt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartModel {
    private int id;
    private int status;
    private Date buyDate;
    private String address;
    private int userId;
}
