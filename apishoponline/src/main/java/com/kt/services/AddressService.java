package com.kt.services;

import com.kt.dtos.TinhDto;

import java.io.IOException;
import java.util.List;

public interface AddressService {
    List<TinhDto> findAllTinh() throws IOException;
}
