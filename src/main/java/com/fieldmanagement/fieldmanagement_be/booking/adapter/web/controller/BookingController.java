package com.fieldmanagement.fieldmanagement_be.booking.adapter.web.controller;

import com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.request.CreateBookingRequest;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.response.BookingDetailResponse;
import com.fieldmanagement.fieldmanagement_be.booking.usecase.BookingUseCase;
import com.fieldmanagement.fieldmanagement_be.common.base.builder.ResponseBuilder;
import com.fieldmanagement.fieldmanagement_be.common.base.dto.ResponseDto;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.StatusCodeEnum;
import com.fieldmanagement.fieldmanagement_be.infra.language.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/bookings")
public class BookingController {
    private final BookingUseCase bookingUseCase;
    private final LanguageService languageService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ResponseDto<BookingDetailResponse>> createBooking(
            @ParameterObject @Valid CreateBookingRequest request
    ) {
        BookingDetailResponse bookingDetailResponse = bookingUseCase.createBooking(request);

        StatusCodeEnum codeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<BookingDetailResponse> response = ResponseBuilder.okResponse(
                codeEnum.code,
                languageService.getMessage(codeEnum.message),
                bookingDetailResponse
        );

        return ResponseEntity
                .status(codeEnum.httpStatusCode)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<BookingDetailResponse>> getBookingDetailById(
            @PathVariable("id") String id
    ) {
        BookingDetailResponse bookingDetailResponse = bookingUseCase.getBookingDetailById(id);

        StatusCodeEnum codeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<BookingDetailResponse> response = ResponseBuilder.okResponse(
                codeEnum.code,
                languageService.getMessage(codeEnum.message),
                bookingDetailResponse
        );

        return ResponseEntity
                .status(codeEnum.httpStatusCode)
                .body(response);
    }
}
