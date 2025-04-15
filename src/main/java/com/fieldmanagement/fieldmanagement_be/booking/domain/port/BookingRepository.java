package com.fieldmanagement.fieldmanagement_be.booking.domain.port;

import com.fieldmanagement.fieldmanagement_be.booking.domain.dto.BookingDTO;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.Booking;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.BookingStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    Booking save(Booking booking);
    Optional<BookingDTO> findById(String id);
    boolean isBookedBySubFieldAndHourlyRateAndDate(String subFiledId, String hourlyRateId, LocalDate date);
    Page<BookingDTO> getAllBookingOfUser(
            String keyword, String id, BookingStatusEnum status,
            LocalDate fromDate, LocalDate toDate, Pageable pageable
    );
}
