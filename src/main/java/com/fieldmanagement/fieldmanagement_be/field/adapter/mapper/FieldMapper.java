package com.fieldmanagement.fieldmanagement_be.field.adapter.mapper;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.dto.FieldEntityDTO;
import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.FieldEntity;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.FieldResponse;
import com.fieldmanagement.fieldmanagement_be.field.domain.dto.FieldDTO;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    FieldEntity toFieldEntity(Field field);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "subFields", ignore = true)
    Field toField(FieldEntity saved);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "subFields", ignore = true)
    FieldDTO toFieldEntityDTO(FieldEntityDTO fieldEntityDTO);

    FieldResponse toFieldResponse(FieldDTO fieldDTO);
}
