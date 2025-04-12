package com.fieldmanagement.fieldmanagement_be.image.adapter.mapper;

import com.fieldmanagement.fieldmanagement_be.image.adapter.db.entity.ImageEntity;
import com.fieldmanagement.fieldmanagement_be.image.domain.model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toImage(ImageEntity imageEntity);

    ImageEntity toImageEntity(Image image);
}
