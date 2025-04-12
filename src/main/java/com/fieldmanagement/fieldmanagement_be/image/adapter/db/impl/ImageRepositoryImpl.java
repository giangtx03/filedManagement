package com.fieldmanagement.fieldmanagement_be.image.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.common.base.enums.ImageTargetTypeEnum;
import com.fieldmanagement.fieldmanagement_be.image.adapter.db.JpaImageRepository;
import com.fieldmanagement.fieldmanagement_be.image.adapter.db.entity.ImageEntity;
import com.fieldmanagement.fieldmanagement_be.image.adapter.mapper.ImageMapper;
import com.fieldmanagement.fieldmanagement_be.image.domain.model.Image;
import com.fieldmanagement.fieldmanagement_be.image.domain.port.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpaImageImpl")
@Primary
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {
    private final JpaImageRepository jpaImageRepository;
    private final ImageMapper imageMapper;

    @Override
    public List<Image> findAllByTargetIdAndTargetType(String targetId, ImageTargetTypeEnum targetType) {
        return jpaImageRepository.findAllByTargetIdAndTargetType(targetId, targetType)
                .stream()
                .map(imageMapper::toImage)
                .toList();
    }

    @Override
    public Optional<Image> findByPath(String path) {
        return jpaImageRepository.findByPath(path)
                .map(imageMapper::toImage);
    }

    @Override
    public Image save(Image image) {
        ImageEntity imageEntity = imageMapper.toImageEntity(image);
        ImageEntity saved = jpaImageRepository.save(imageEntity);
        return imageMapper.toImage(saved);
    }

    @Override
    public void delete(Image image) {
        ImageEntity imageEntity = imageMapper.toImageEntity(image);
        jpaImageRepository.delete(imageEntity);
    }
}
