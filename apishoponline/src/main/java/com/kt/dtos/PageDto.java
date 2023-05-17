package com.kt.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> implements Serializable {
    private int index;
    private int total;
    private List<T> content;
}
