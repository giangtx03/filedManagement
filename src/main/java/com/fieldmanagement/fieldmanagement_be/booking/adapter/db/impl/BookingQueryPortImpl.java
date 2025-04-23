package com.fieldmanagement.fieldmanagement_be.booking.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.booking.domain.dto.BookingDTO;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.Booking;
import com.fieldmanagement.fieldmanagement_be.booking.domain.port.BookingQueryPort;
import com.fieldmanagement.fieldmanagement_be.booking.domain.port.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class BookingQueryPortImpl implements BookingQueryPort {
    private final BookingRepository bookingRepository;

    @Override
    public Optional<BookingDTO> findById(String id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }
}
