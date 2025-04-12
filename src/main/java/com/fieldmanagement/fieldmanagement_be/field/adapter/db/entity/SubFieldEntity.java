package com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.entity.BookingEntity;
import com.fieldmanagement.fieldmanagement_be.common.base.entity.BaseEntity;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.FieldTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "SubField")
@Table(name = "sub_fields")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubFieldEntity extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "name", nullable = false, length = 64)
    String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    FieldTypeEnum type;

    @OneToMany(mappedBy = "subField")
    List<BookingEntity> bookings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false, updatable = false)
    FieldEntity field;

    @ManyToMany(mappedBy = "subFields")
    List<HourlyRateEntity> hourlyRates;
}
