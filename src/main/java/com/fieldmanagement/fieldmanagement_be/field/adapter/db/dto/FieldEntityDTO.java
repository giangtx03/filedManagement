package com.fieldmanagement.fieldmanagement_be.field.adapter.db.dto;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.FieldEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldEntityDTO extends FieldEntity {
    private Double avgStar;
    private List<String> images;

    public FieldEntityDTO(FieldEntity field, Double avgStar) {
        this.setBaseField(field);
        this.avgStar = avgStar;
    }

    private void setBaseField(FieldEntity field) {
        super.setId(field.getId());
        super.setName(field.getName());
        super.setLocation(field.getLocation());
        super.setDescription(field.getDescription());
        super.setOwner(field.getOwner());
        super.setSubFields(field.getSubFields());
        super.setCreatedAt(field.getCreatedAt());
        super.setUpdatedAt(field.getUpdatedAt());
        super.setDeletedAt(field.getDeletedAt());
    }
}
