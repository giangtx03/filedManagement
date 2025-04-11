package com.fieldmanagement.fieldmanagement_be.image.adapter.db.entity;

import com.fieldmanagement.fieldmanagement_be.common.base.entity.BaseEntity;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.ImageTargetTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@SuperBuilder
@Entity(name = "Image")
@Table(name = "images")
public class ImageEntity extends BaseEntity {
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
