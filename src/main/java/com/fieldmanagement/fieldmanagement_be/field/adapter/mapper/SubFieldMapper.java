package com.fieldmanagement.fieldmanagement_be.field.adapter.mapper;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.SubFieldEntity;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.SubFieldResponse;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.SubField;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubFieldMapper {
    SubFieldResponse toSubFieldResponse(SubField subField);
    SubField toSubField(SubFieldEntity subFieldEntity);
}
