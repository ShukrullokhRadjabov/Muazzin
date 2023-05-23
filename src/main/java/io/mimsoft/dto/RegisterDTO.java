package io.mimsoft.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String password;
}
