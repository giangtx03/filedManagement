package com.fieldmanagement.fieldmanagement_be.field.domain.dto;

import com.fieldmanagement.fieldmanagement_be.field.domain.model.Field;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class FieldDTO extends Field {
    private Double avgStar;
    private List<String> images;
}
