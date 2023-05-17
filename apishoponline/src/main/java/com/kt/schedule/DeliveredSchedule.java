package com.kt.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kt.services.CartItemService;

@Component
public class DeliveredSchedule {
    @Autowired
    CartItemService cartItemService;
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleverd() {
        cartItemService.deliveredCartItem();
    }
}
