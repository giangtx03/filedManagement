package com.fieldmanagement.commom.model.entity;

import com.fieldmanagement.commom.model.enums.ImageTargetTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageBaseModel extends BaseModel {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "image_path", nullable = false)
    String imagePath;

    @Column(name = "target_id", nullable = false)
    String targetId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "target_type", nullable = false)
    ImageTargetTypeEnum targetType;
}
