package io.mimsoft.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    private String id;
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdDate;
}
