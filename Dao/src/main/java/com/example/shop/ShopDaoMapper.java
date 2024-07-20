package com.example.shop;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopDaoMapper {

    ShopDao mapToDao(Shop shop);

    Shop mapToModel(ShopDao shopDao);

    List<Shop> mapToModelList(List<ShopDao> shopDaoList);

}