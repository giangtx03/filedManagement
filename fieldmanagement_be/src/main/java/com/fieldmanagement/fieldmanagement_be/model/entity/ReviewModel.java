package com.fieldmanagement.fieldmanagement_be.model.entity;

import com.fieldmanagement.commom.model.entity.ReviewBaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "ReviewModel")
@Table(name = "reviews")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewModel extends ReviewBaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "large_field_id", nullable = false, updatable = false)
    LargeFieldModel largeField;
}
