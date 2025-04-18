package com.fieldmanagement.fieldmanagement_be.image.domain.port;

import com.fieldmanagement.fieldmanagement_be.image.domain.model.ImageTargetTypeEnum;

import java.util.List;

public interface ImageQueryPort {
    List<String> getImagePathsByTarget(String targetId, ImageTargetTypeEnum type);
}
