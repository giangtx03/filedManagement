package com.fieldmanagement.fieldmanagement_be.booking.adapter.db.dto;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.entity.BookingEntity;
import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.FieldEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingEntityDTO extends BookingEntity {
    private FieldEntity field;

    public BookingEntityDTO(BookingEntity bookingEntity, FieldEntity field) {
        setBaseBookingEntity(bookingEntity);
        this.field = field;
    }

    private void setBaseBookingEntity(BookingEntity bookingEntity) {
        super.setId(bookingEntity.getId());
        super.setBookingDate(bookingEntity.getBookingDate());
        super.setNote(bookingEntity.getNote());
        super.setStatus(bookingEntity.getStatus());
        super.setUser(bookingEntity.getUser());
        super.setHourlyRate(bookingEntity.getHourlyRate());
        super.setSubField(bookingEntity.getSubField());
        super.setCreatedAt(bookingEntity.getCreatedAt());
        super.setUpdatedAt(bookingEntity.getUpdatedAt());
        super.setDeletedAt(bookingEntity.getDeletedAt());
    }
}
