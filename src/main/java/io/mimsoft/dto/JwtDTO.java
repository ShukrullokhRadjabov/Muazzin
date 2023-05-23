package io.mimsoft.dto;

import io.mimsoft.enums.UserRole;
import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtDTO {
    private String phone;
    private UserRole role;

}
