package com.fieldmanagement.commom.model.entity;

import com.fieldmanagement.commom.model.enums.BookingStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingBaseModel extends BaseModel {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "note")
    String note;

    @Column(name = "booking_date", updatable = false)
    OffsetDateTime bookingDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    BookingStatusEnum status;
}
