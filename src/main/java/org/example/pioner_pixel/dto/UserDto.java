package org.example.pioner_pixel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String dateOfBirth;
    private List<EmailDto> emailData;
    private List<PhoneDto> phoneData;
    private AccountDto account;
}
