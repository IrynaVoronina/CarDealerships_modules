package com.example.car;

import com.example.car.dto.CarRequestDto;
import com.example.car.dto.CarResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CarServiceMapper {

    Car mapToModel(CarRequestDto requestDto);

    CarResponseDto mapToDto(Car car);

    List<CarResponseDto> mapToDtoList(List<Car> carList);

}