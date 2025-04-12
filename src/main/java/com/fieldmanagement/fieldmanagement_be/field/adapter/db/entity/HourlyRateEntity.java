package com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.entity.BookingEntity;
import com.fieldmanagement.fieldmanagement_be.common.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "HourlyRate")
@Table(name = "hourly_rates")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HourlyRateEntity extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "start_time", nullable = false)
    LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    LocalTime endTime;

    @Column(name = "price", nullable = false)
    Float price;

    @OneToMany(mappedBy = "hourlyRate")
    List<BookingEntity> bookings;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "hourly_fields",
            joinColumns = @JoinColumn(name = "hourly_rate_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_field_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"hourly_rate_id", "sub_field_id"})
    )
    List<SubFieldEntity> subFields;
}
