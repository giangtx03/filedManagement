package com.fieldmanagement.fieldmanagement_be.image.domain.model;

import com.fieldmanagement.fieldmanagement_be.common.base.enums.ImageTargetTypeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
    String id;
    String path;
    String targetId;
    ImageTargetTypeEnum targetType;
}
