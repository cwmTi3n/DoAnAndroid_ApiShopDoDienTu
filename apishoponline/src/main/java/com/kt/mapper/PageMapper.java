package com.kt.mapper;

import com.kt.dtos.PageDto;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
public class PageMapper<T> {
    public PageDto<T> toPageDto(Page<T> page) {
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setIndex(page.getNumber());
        pageDto.setTotal(page.getTotalPages());
        pageDto.setContent(page.getContent());
        return pageDto;
    }
}
