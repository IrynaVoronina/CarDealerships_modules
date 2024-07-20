package com.example.cars_in_shop.inventory_event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryEvent {

    Integer id;

    LocalDate date;

    int carId;

    String shoppId;

}
