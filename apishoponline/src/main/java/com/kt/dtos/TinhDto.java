package com.kt.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TinhDto {
    private String name;
    private String slug;
    private String type;
    private String name_with_type;
    private String code;
    List<HuyenDto> districts;
}
