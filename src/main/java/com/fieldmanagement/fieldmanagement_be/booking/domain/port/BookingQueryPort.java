package com.fieldmanagement.fieldmanagement_be.booking.domain.port;

import com.fieldmanagement.fieldmanagement_be.booking.domain.dto.BookingDTO;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.Booking;

import java.util.Optional;

public interface BookingQueryPort {
    Optional<BookingDTO> findById(String id);
    Booking save(Booking booking);
}
