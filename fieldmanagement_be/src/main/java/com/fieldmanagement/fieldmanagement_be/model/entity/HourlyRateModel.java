package com.fieldmanagement.fieldmanagement_be.model.entity;

import com.fieldmanagement.commom.model.entity.HourlyRateBaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Entity(name = "HourlyRateModel")
@Table(name = "hourly_rates")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HourlyRateModel extends HourlyRateBaseModel {
    @OneToMany(mappedBy = "hourlyRate")
    List<BookingModel> bookings;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "hourly_fields",
            joinColumns = @JoinColumn(name = "hourly_rate_id"),
            inverseJoinColumns = @JoinColumn(name = "small_field_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"hourly_rate_id", "small_field_id"})
    )
    List<SmallFieldModel> smallFields;
}
