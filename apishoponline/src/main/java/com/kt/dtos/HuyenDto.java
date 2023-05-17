package com.kt.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuyenDto {
    private String name;
    private String type;
    private String slug;
    private String name_with_type;
    private String path;
    private String path_with_type;
    private String code;
    private String parent_code;
    List<XaDto> wards;
}
