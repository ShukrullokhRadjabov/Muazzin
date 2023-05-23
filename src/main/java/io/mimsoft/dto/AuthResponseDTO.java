package io.mimsoft.dto;

import io.mimsoft.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    private String name;
    private String surname;
    private UserRole role;
    private String jwt;
}
