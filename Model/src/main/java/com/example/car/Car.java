package com.example.car;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car {

    Integer id;
    String name;
    int doorsCount;
    boolean isBuyEnable;

}
