package com.kt.controllers.web;

import com.kt.dtos.TinhDto;
import com.kt.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class AddressController {
    @Autowired
    AddressService addressService;
    @GetMapping("address")
    public List<TinhDto> test() throws IOException {
        return addressService.findAllTinh();
    }
}
