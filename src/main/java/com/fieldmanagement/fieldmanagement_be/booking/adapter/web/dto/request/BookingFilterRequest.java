package com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.request;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.SearchRequest;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.BookingStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class BookingFilterRequest extends SearchRequest {
    private LocalDate fromDate;
    private LocalDate toDate;
    private BookingStatusEnum status;
}
