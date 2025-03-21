package com.fieldmanagement.fieldmanagement_be.model.entity;

import com.fieldmanagement.commom.model.entity.SmallFieldBaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity(name = "SmallFieldModel")
@Table(name = "small_fields")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmallFieldModel extends SmallFieldBaseModel {
    @OneToMany(mappedBy = "smallField")
    List<BookingModel> bookings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "large_field_id", nullable = false, updatable = false)
    LargeFieldModel largeField;
}
