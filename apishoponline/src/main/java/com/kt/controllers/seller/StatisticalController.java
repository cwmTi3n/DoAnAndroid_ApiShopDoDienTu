package com.kt.controllers.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.dtos.StatisticsDto;
import com.kt.services.CartItemService;
import com.kt.utils.Constant;

@RestController
@RequestMapping("seller")
public class StatisticalController {
    @Autowired
    CartItemService cartItemService;
    @GetMapping("statistics-shop")
    public StatisticsDto getStatistics() {
        int sellerId = Constant.getUserLogin().getId();
        StatisticsDto statisticsDto = new StatisticsDto();
        int deleveringOrder = cartItemService.totalOrder(sellerId, 2);
        int deleveredOrder = cartItemService.totalOrder(sellerId, 3);
        int cancelOrder = cartItemService.totalOrder(sellerId, 4);
        int totalOrder = deleveredOrder + deleveringOrder + cancelOrder + cartItemService.totalOrder(sellerId, 1);
        float revenue = cartItemService.revenue(sellerId, 3);
        statisticsDto.setCancelOrder(cancelOrder);
        statisticsDto.setDeliveredOrder(deleveredOrder);
        statisticsDto.setDeliveringOrder(deleveringOrder);
        statisticsDto.setTotalOrder(totalOrder);
        statisticsDto.setRevenue(revenue);
        return statisticsDto;
    }
}
