package com.kt.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private int id;
    private int status;
    private Date buyDate;
    private String avatar;
    private int sumProduct;
    private int sumPrice;
    private String address;
    private int userId;
    private String username;
    private String phone;
}
