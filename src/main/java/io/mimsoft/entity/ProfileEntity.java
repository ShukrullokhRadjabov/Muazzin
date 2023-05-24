package io.mimsoft.entity;

import io.mimsoft.enums.UserRole;
import io.mimsoft.template.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(callSuper = true)
@Data

@NoArgsConstructor
@Entity(name = "profile")
public class ProfileEntity extends AbstractEntity {
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String sessionCode;

    public ProfileEntity(String name, String surname, String phone, String email, String password, UserRole role) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
