package io.mimsoft.dto;

import io.mimsoft.entity.AttachEntity;
import lombok.Data;

@Data
public class AsmaUlHusnaResponseDTO {
    private String arabicTitle;
    private String title;
    private String description;
    private String info;
    private AttachEntity attach;

}
