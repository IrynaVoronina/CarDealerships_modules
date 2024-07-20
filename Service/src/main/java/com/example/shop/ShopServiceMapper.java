package com.example.shop;

import com.example.shop.dto.ShopRequestDto;
import com.example.shop.dto.ShopResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ShopServiceMapper {

    Shop mapToModel(ShopRequestDto requestDto);

    ShopResponseDto mapToDto(Shop shop);

    List<ShopResponseDto> mapToDtoList(List<Shop> shopList);

}