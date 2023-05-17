package com.kt.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDto {
    private int totalOrder;
    private int deliveringOrder;
    private int deliveredOrder;
    private int cancelOrder;
    private float revenue;
}
