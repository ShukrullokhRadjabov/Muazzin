package io.mimsoft.entity;

import io.mimsoft.template.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AsmaUlHusnaEntity extends AbstractEntity {
    private String arabicTitle;
    @Column()
    private String titleUzb;
    @Column()
    private String titleRu;
    @Column()
    private String titleEng;
    @Column(columnDefinition = "text")
    private String descriptionUzb;
    @Column(columnDefinition = "text")
    private String descriptionRu;
    @Column(columnDefinition = "text")
    private String descriptionEng;
    @Column(columnDefinition = "text")
    private String infoUzb;
    @Column(columnDefinition = "text")
    private String infoRu;
    @Column(columnDefinition = "text")
    private String infoEng;
    private String attachId;
}
