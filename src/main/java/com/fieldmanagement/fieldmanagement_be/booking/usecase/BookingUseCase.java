package com.fieldmanagement.fieldmanagement_be.booking.usecase;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.mapper.BookingMapper;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.response.BookingDetailResponse;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.Booking;
import com.fieldmanagement.fieldmanagement_be.booking.domain.port.BookingRepository;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.ImageTargetTypeEnum;
import com.fieldmanagement.fieldmanagement_be.image.domain.port.ImageQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingUseCase {
    private final BookingRepository bookingRepository;
    private final ImageQueryPort imageQueryPort;
    private final BookingMapper bookingMapper;

    public BookingDetailResponse createBooking() {
        return null;
    }

    public BookingDetailResponse getBookingDetailById(String id) {
        BookingDetailResponse response = bookingRepository.findById(id)
                .map(bookingMapper::toBookingDetailResponse)
                .orElseThrow();
        List<String> images = imageQueryPort.getImagePathsByTarget(
                response.getField().getId(), ImageTargetTypeEnum.FIELD
        );

        response.getField().setImages(images);
        return response;
    }
}
