package com.fieldmanagement.fieldmanagement_be.booking.domain.port;

import com.fieldmanagement.fieldmanagement_be.booking.domain.dto.BookingDTO;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    Booking save(Booking booking);
    Optional<BookingDTO> findById(String id);
    boolean isBookedBySubFieldAndHourlyRateAndDate(String subFiledId, String hourlyRateId, LocalDate date);
}
