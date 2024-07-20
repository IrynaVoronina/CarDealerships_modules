package com.example.car;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarDaoMapper {

    CarDao mapToDao(Car car);

    Car mapToModel(CarDao carDao);

    List<Car> mapToModelList(List<CarDao> carDaoList);

}
