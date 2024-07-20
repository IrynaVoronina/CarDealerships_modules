package com.example.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopRequestDto {

    @NotBlank(message = "Name must be specified")
    @Size(max = 256, message = "Name should not be greater than 256 chars")
    String name;

    @Range(min = 1, max = 1000, message = "Amount of places must be between 1 and 1000")
    int placesAmount;

}
