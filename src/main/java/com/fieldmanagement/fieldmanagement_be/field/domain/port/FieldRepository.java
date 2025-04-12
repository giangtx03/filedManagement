package com.fieldmanagement.fieldmanagement_be.field.domain.port;

import com.fieldmanagement.fieldmanagement_be.field.domain.dto.FieldDTO;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FieldRepository {
    Field save(Field field);
    Optional<Field> findById(String id);
    Optional<Field> findByUrlSlug(String urlSlug);
    Page<FieldDTO> getAllFields(
            String keyword, String location,
            Float startPrice, Float endPrice,
            Float startTime, Float endTime, Pageable pageable);
    void softDelete(Field field);
}
