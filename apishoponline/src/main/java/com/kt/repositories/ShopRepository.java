package com.kt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.entities.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer>{
    
}
