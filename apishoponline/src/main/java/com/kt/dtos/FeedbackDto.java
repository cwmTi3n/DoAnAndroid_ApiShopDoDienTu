package com.kt.dtos;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    private int id;
    private String content;
    private int star;
    private String image;
    private String avatar;
    private String username;
    private Date time;
}
