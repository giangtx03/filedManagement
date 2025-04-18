package com.fieldmanagement.fieldmanagement_be.booking.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.JpaBookingRepository;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.entity.BookingEntity;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.mapper.BookingMapper;
import com.fieldmanagement.fieldmanagement_be.booking.domain.dto.BookingDTO;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.Booking;
import com.fieldmanagement.fieldmanagement_be.booking.domain.port.BookingRepository;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.BookingStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Primary
@Repository("jpaBookingImpl")
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {
    private final JpaBookingRepository jpaBookingRepository;
    private final BookingMapper bookingMapper;
    @Override
    public Booking save(Booking booking) {
        BookingEntity bookingEntity = bookingMapper.toBookingEntity(booking);
        BookingEntity saved = jpaBookingRepository.save(bookingEntity);
        return bookingMapper.toBooking(saved);
    }

    @Override
    public Optional<BookingDTO> findById(String id) {
        return jpaBookingRepository.findDtoById(id)
                .map(bookingMapper::toBookingDTO);
    }

    @Override
    public boolean isBookedBySubFieldAndHourlyRateAndDate(
            String subFiledId, String hourlyRateId, LocalDate date
    ) {
        return jpaBookingRepository.isBookedBySubFieldAndHourlyRateAndDate(
                subFiledId, hourlyRateId, date
        );
    }

    @Override
    public Page<BookingDTO> getAllBookingOfUser(
            String keyword, String id, BookingStatusEnum status,
            LocalDate fromDate, LocalDate toDate, Pageable pageable
    ) {
        return jpaBookingRepository.findAllByUserWithFilter(keyword, id, status,
                fromDate, toDate, pageable)
                .map(bookingMapper::toBookingDTO);
    }
}
