package com.kt.services.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.dtos.HuyenDto;
import com.kt.dtos.TinhDto;
import com.kt.dtos.XaDto;
import com.kt.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    ResourceLoader resourceLoader;
    @Override
    public List<TinhDto> findAllTinh() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:address.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        Gson gson = new Gson();
//        Type provinceMapType = new TypeToken<Map<String, TinhDto>>() {}.getType();
//        Map<String, TinhDto> provinces = gson.fromJson(reader, provinceMapType);
        Type type = new TypeToken<List<TinhDto>>() {}.getType();
        List<TinhDto> tinhDtos = gson.fromJson(reader, type);
        reader.close();

//        List<TinhDto> tinhDtos = new ArrayList<>();
//        for(Map.Entry<String, TinhDto> entry : provinces.entrySet()) {
//            entry.getValue().setDistricts(new ArrayList<>());
//            tinhDtos.add(entry.getValue());
//        }
//
//        Map<String, HuyenDto> districts = getMapHuyenDto();
//        for(Map.Entry<String, HuyenDto> entry : districts.entrySet()) {
//            HuyenDto huyenDto = entry.getValue();
//            huyenDto.setWards(new ArrayList<>());
//            provinces.get(huyenDto.getParent_code()).getDistricts().add(huyenDto);
//        }
//
//        Map<String, XaDto> wards = getMapXaDto();
//        for(Map.Entry<String, XaDto> entry : wards.entrySet()) {
//            XaDto xaDto = entry.getValue();
//            districts.get(xaDto.getParent_code()).getWards().add(xaDto);
//        }
        return tinhDtos;
    }

    public Map<String, HuyenDto> getMapHuyenDto() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:quan_huyen.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        Gson gson = new Gson();
        Type districtMapType = new TypeToken<Map<String, HuyenDto>>() {}.getType();
        Map<String, HuyenDto> districts = gson.fromJson(reader, districtMapType);
        reader.close();
        return districts;
    }

    public Map<String, XaDto> getMapXaDto() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:xa_phuong.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        Gson gson = new Gson();
        Type wardMapType = new TypeToken<Map<String, XaDto>>() {}.getType();
        Map<String, XaDto> wards = gson.fromJson(reader, wardMapType);
        reader.close();
        return wards;
    }
}
