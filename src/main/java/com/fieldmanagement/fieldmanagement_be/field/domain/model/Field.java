package com.fieldmanagement.fieldmanagement_be.field.domain.model;

import com.fieldmanagement.fieldmanagement_be.common.base.model.BaseModel;
import com.fieldmanagement.fieldmanagement_be.review.domain.model.Review;
import com.fieldmanagement.fieldmanagement_be.user.adapter.db.entity.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Field extends BaseModel {
    String id;
    String name;
    String urlSlug;
    String location;
    String description;
    UserEntity owner;
    List<SubField> subFields;
    List<Review> reviews;
}
