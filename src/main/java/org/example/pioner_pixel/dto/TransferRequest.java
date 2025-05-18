package org.example.pioner_pixel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

    @NotNull(message = "Сумма обязательна")
    @Min(value = 1, message = "Сумма перевода должна быть больше нуля")
    private BigDecimal amount;
}
