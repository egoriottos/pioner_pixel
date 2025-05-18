package org.example.pioner_pixel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSearchResult {
    private Long id;
    private String name;
    private String dateOfBirth;
    private List<String> emails;
    private List<String> phones;
    private BigDecimal balance;
}
