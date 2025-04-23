package com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.request;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.SearchRequest;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.BookingStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class BookingFilterRequest extends SearchRequest {
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fromDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate toDate;
    private BookingStatusEnum status;
}
