package org.example.pioner_pixel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Email(message = "Mail must be valid")
    @NotBlank(message = "email can not ve null")
    private String email;
    @NotBlank(message = "requires password")
    private String password;
}
