package com.kt.services;

import com.kt.entities.Shop;

public interface ShopSercive {
    <S extends Shop> S sava(S entity);
    Shop findById(int id);
}
