package com.example.shop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopDao {

    @NotBlank
    String id;

    @NotBlank
    @Size(max = 256)
    String name;

    @Range(min = 1, max = 1000)
    int placesAmount;

}