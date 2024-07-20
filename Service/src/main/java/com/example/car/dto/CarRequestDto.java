package com.example.car.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarRequestDto {

    @NotBlank(message = "Name must be specified")
    @Size(max = 256, message = "Should not be greater than 256 chars")
    String name;

    @Range(min = 1, max = 10, message = "Doors count must be between 1 and 10")
    int doorsCount;

    boolean isBuyEnable;
}
