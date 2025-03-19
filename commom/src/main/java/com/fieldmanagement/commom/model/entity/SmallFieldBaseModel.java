package com.fieldmanagement.commom.model.entity;

import com.fieldmanagement.commom.model.enums.FieldTypeEnum;
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
public class SmallFieldBaseModel extends  BaseModel{
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "name", nullable = false, length = 64)
    String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    FieldTypeEnum type;
}
