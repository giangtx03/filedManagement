package com.fieldmanagement.fieldmanagement_be.image.adapter.db;

import com.fieldmanagement.fieldmanagement_be.image.domain.model.ImageTargetTypeEnum;
import com.fieldmanagement.fieldmanagement_be.image.adapter.db.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaImageRepository extends JpaRepository<ImageEntity, String> {
    Optional<ImageEntity> findByPath(String path);

    List<ImageEntity> findAllByTargetIdAndTargetType(String targetId, ImageTargetTypeEnum targetType);
}
