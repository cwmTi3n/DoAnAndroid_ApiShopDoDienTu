package com.kt.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.entities.Shop;
import com.kt.repositories.ShopRepository;
import com.kt.services.ShopSercive;

@Service
public class ShopServiceImpl implements ShopSercive{
    @Autowired
    ShopRepository shopRepository;
    @Override
    public <S extends Shop> S sava(S entity) {
        return shopRepository.save(entity);
    }
    @Override
    public Shop findById(int id) {
        return shopRepository.findById(id).orElse(null);
    }
    
}
