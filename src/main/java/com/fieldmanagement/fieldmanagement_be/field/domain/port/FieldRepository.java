package com.fieldmanagement.fieldmanagement_be.field.domain.port;

import com.fieldmanagement.fieldmanagement_be.field.domain.dto.FieldDTO;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.Optional;

public interface FieldRepository {
    Field save(Field field);
    Optional<Field> findById(String id);
    Optional<FieldDTO> findByUrlSlug(String urlSlug);
    Page<FieldDTO> getAllFields(
            String keyword, String location,
            Float startPrice, Float endPrice,
            LocalTime fromTime, LocalTime toTime, Pageable pageable);
    void softDelete(Field field);
}
