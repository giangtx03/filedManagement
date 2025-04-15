package com.fieldmanagement.fieldmanagement_be.booking.usecase;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.mapper.BookingMapper;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.response.BookingDetailResponse;
import com.fieldmanagement.fieldmanagement_be.booking.domain.port.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingUseCase {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingDetailResponse getBookingDetailById(String id) {
        BookingDetailResponse response = bookingRepository.findById(id)
                .map(bookingMapper::toBookingDetailResponse)
                .orElseThrow();

        return response;
    }
}
