package io.mimsoft.dto;

import io.mimsoft.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtDTO {

    private String sessionCode;
    private String phone;
    private UserRole role;

}
