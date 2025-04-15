package com.fieldmanagement.fieldmanagement_be.booking.domain.dto;

import com.fieldmanagement.fieldmanagement_be.booking.domain.model.Booking;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.Field;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BookingDTO extends Booking {
    private Field field;
}
