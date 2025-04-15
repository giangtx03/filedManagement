package com.fieldmanagement.fieldmanagement_be.booking.adapter.mapper;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.dto.BookingEntityDTO;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.entity.BookingEntity;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.response.BookingDetailResponse;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.response.BookingResponse;
import com.fieldmanagement.fieldmanagement_be.booking.domain.dto.BookingDTO;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingEntity toBookingEntity(Booking booking);

    Booking toBooking(BookingEntity saved);

    BookingDTO toBookingDTO(BookingEntityDTO bookingEntityDTO);

    BookingDetailResponse toBookingDetailResponse(BookingDTO bookingDTO);

    BookingResponse toBookingResponse(BookingDTO bookingDTO);
}
