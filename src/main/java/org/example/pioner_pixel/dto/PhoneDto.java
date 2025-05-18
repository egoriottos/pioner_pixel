package org.example.pioner_pixel.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhoneDto {
    @Pattern(
            regexp = "^(\\+7|8)\\d{10}$",
            message = "Телефон должен начинаться с +7 или 8 и содержать 11 цифр"
    )
    private String phone;
}
