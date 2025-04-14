package com.fieldmanagement.fieldmanagement_be.image.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.common.base.enums.ImageTargetTypeEnum;
import com.fieldmanagement.fieldmanagement_be.image.domain.model.Image;
import com.fieldmanagement.fieldmanagement_be.image.domain.port.ImageQueryPort;
import com.fieldmanagement.fieldmanagement_be.image.domain.port.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ImageQueryAdapter implements ImageQueryPort {
    private final ImageRepository imageRepository;
    @Override
    public List<String> getImagePathsByTarget(String targetId, ImageTargetTypeEnum type) {
        return imageRepository.findAllByTargetIdAndTargetType(targetId, type)
                .stream()
                .map(Image::getPath)
                .toList();
    }
}
