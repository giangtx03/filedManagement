package com.fieldmanagement.fieldmanagement_be.image.domain.port;

import com.fieldmanagement.fieldmanagement_be.common.base.enums.ImageTargetTypeEnum;
import com.fieldmanagement.fieldmanagement_be.image.domain.model.Image;

import java.util.List;
import java.util.Optional;

public interface ImageRepository {
    List<Image> findAllByTargetIdAndTargetType(String targetId, ImageTargetTypeEnum targetType);

    Optional<Image> findByPath(String path);

    Image save(Image image);

    void delete(Image image);
}
