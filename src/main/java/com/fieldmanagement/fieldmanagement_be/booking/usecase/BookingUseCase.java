package com.fieldmanagement.fieldmanagement_be.booking.usecase;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.mapper.BookingMapper;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.request.BookingFilterRequest;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.request.CreateBookingRequest;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.response.BookingDetailResponse;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.response.BookingResponse;
import com.fieldmanagement.fieldmanagement_be.booking.domain.dto.BookingDTO;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.Booking;
import com.fieldmanagement.fieldmanagement_be.booking.domain.port.BookingRepository;
import com.fieldmanagement.fieldmanagement_be.booking.exception.BookingFailedException;
import com.fieldmanagement.fieldmanagement_be.common.base.dto.PageResult;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.BookingStatusEnum;
import com.fieldmanagement.fieldmanagement_be.image.domain.model.ImageTargetTypeEnum;
import com.fieldmanagement.fieldmanagement_be.common.helper.MetaDataHelper;
import com.fieldmanagement.fieldmanagement_be.common.helper.SortHelper;
import com.fieldmanagement.fieldmanagement_be.common.util.SecurityUtils;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.HourlyRate;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.SubField;
import com.fieldmanagement.fieldmanagement_be.field.domain.port.HourlyRateQueryPort;
import com.fieldmanagement.fieldmanagement_be.field.domain.port.SubFieldQueryPort;
import com.fieldmanagement.fieldmanagement_be.field.exception.BookingNotFoundException;
import com.fieldmanagement.fieldmanagement_be.field.exception.HourlyRateNotFoundException;
import com.fieldmanagement.fieldmanagement_be.field.exception.SubFieldNotFoundException;
import com.fieldmanagement.fieldmanagement_be.image.domain.port.ImageQueryPort;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;
import com.fieldmanagement.fieldmanagement_be.user.domain.port.UserQueryPort;
import com.fieldmanagement.fieldmanagement_be.user.exception.UserDoesNotHavePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingUseCase {
    private final BookingRepository bookingRepository;
    private final ImageQueryPort imageQueryPort;
    private final UserQueryPort userQueryPort;
    private final SubFieldQueryPort subFieldQueryPort;
    private final HourlyRateQueryPort hourlyRateQueryPort;
    private final BookingMapper bookingMapper;

    @Transactional
    public BookingDetailResponse createBooking(CreateBookingRequest request) {
        String email = SecurityUtils.getUserEmailFromSecurity();
        User user = userQueryPort.findByEmail(email)
                .orElseThrow(() -> new UserDoesNotHavePermission("Người dùng không có quyền truy cập"));

        SubField subField = subFieldQueryPort.findById(request.getSubFieldId())
                .orElseThrow(() -> new SubFieldNotFoundException("Không tìm thấy sân"));

        HourlyRate hourlyRate = hourlyRateQueryPort.findById(request.getHourlyRateId())
                .orElseThrow(() -> new HourlyRateNotFoundException("Không tìm thấy khung giờ"));

        validateBookingRequest(request.getSubFieldId(), request.getHourlyRateId(), request.getDate(), subField);

        Booking booking = Booking.builder()
                .bookingDate(request.getDate())
                .hourlyRate(hourlyRate)
                .subField(subField)
                .note(request.getNote())
                .user(user)
                .status(BookingStatusEnum.PENDING)
                .build();

        Booking saved = bookingRepository.save(booking);
        return getBookingDetailById(saved.getId());
    }

    private void validateBookingRequest(String subFieldId, String hourlyRateId, LocalDate date, SubField subField) {
        boolean isBooked = bookingRepository.isBookedBySubFieldAndHourlyRateAndDate(subFieldId, hourlyRateId, date);
        boolean isValid = subField.getHourlyRates().stream().anyMatch(hr -> hr.getId().equals(hourlyRateId));

        if (isBooked) throw new BookingFailedException("Khung giờ đã được đặt");
        if (!isValid) throw new BookingFailedException("Khung giờ không hợp lệ với sân đã chọn");
        if (date.isBefore(LocalDate.now())) {
            throw new BookingFailedException("Không thể đặt sân cho ngày trong quá khứ");
        }
    }

    public BookingDetailResponse getBookingDetailById(String id) {
        BookingDetailResponse response = bookingRepository.findById(id)
                .map(bookingMapper::toBookingDetailResponse)
                .map(bookingResponse -> {
                    List<String> images = imageQueryPort.getImagePathsByTarget(
                            bookingResponse.getField().getId(), ImageTargetTypeEnum.FIELD
                    );

                    bookingResponse.getField().setImages(images);
                    return bookingResponse;
                })
                .orElseThrow(() -> new BookingNotFoundException(""));
        return response;
    }

    public PageResult<List<BookingResponse>> getBookingOfUser(BookingFilterRequest request) {
        String email = SecurityUtils.getUserEmailFromSecurity();
        User user = userQueryPort.findByEmail(email)
                .orElseThrow(() -> new UserDoesNotHavePermission("Người dùng không có quyền truy cập"));

        Sort sort = SortHelper.buildSort(request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        Page<BookingDTO> bookingDTOS = bookingRepository.getAllBookingOfUser(
                request.getKeyword(), user.getId(), request.getStatus(),
                request.getFromDate(), request.getToDate(), pageable
        );

        List<BookingResponse> bookingResponses = bookingDTOS.getContent()
                .stream()
                .map(bookingMapper::toBookingResponse)
                .peek(bookingResponse -> {
                    List<String> images = imageQueryPort.getImagePathsByTarget(
                            bookingResponse.getField().getId(), ImageTargetTypeEnum.FIELD
                    );

                    bookingResponse.getField().setImages(images);
                })
                .toList();

        return PageResult.<List<BookingResponse>>builder()
                .data(bookingResponses)
                .metaData(MetaDataHelper.buildMetaData(bookingDTOS, request))
                .build();
    }
}
