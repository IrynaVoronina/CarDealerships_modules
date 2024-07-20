package com.example.car.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarResponseDto {

    Integer id;
    String name;
    int doorsCount;
    boolean isBuyEnable;

}