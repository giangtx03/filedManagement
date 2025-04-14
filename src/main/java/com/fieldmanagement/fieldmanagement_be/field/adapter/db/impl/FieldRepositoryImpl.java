package com.fieldmanagement.fieldmanagement_be.field.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.JpaFieldRepository;
import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.FieldEntity;
import com.fieldmanagement.fieldmanagement_be.field.adapter.mapper.FieldMapper;
import com.fieldmanagement.fieldmanagement_be.field.domain.dto.FieldDTO;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.Field;
import com.fieldmanagement.fieldmanagement_be.field.domain.port.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Primary
@RequiredArgsConstructor
@Repository("jpaFieldImpl")
public class FieldRepositoryImpl implements FieldRepository {
    private final JpaFieldRepository jpaFieldRepository;
    private final FieldMapper fieldMapper;

    @Override
    public Field save(Field field) {
        FieldEntity fieldEntity = fieldMapper.toFieldEntity(field);
        FieldEntity saved = jpaFieldRepository.save(fieldEntity);
        return fieldMapper.toField(saved);
    }

    @Override
    public Optional<Field> findById(String id) {
        return jpaFieldRepository.findById(id)
                .map(fieldMapper::toField);
    }

    @Override
    public Optional<FieldDTO> findByUrlSlug(String urlSlug) {
        return jpaFieldRepository.findByUrlSlug(urlSlug)
                .map(fieldMapper::toFieldDTO);
    }

    @Override
    public Page<FieldDTO> getAllFields(
            String keyword, String location,
            Float startPrice, Float endPrice,
            Float startTime, Float endTime, Pageable pageable) {
        return jpaFieldRepository.findAllField(
                keyword, location,
                startPrice, endPrice,
                startTime, endTime, pageable)
                .map(fieldMapper::toFieldDTO);
    }

    @Override
    public void softDelete(Field field) {
        FieldEntity fieldEntity = fieldMapper.toFieldEntity(field);
        fieldEntity.setDeletedAt(OffsetDateTime.now());
        jpaFieldRepository.save(fieldEntity);
    }
}
