package com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.response;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.BaseResponse;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.BookingStatusEnum;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.FieldResponse;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.HourlyRateResponse;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.SubFieldResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse extends BaseResponse {
    String id;
    FieldResponse field;
    SubFieldResponse subField;
    HourlyRateResponse hourlyRate;
    BookingStatusEnum status;
    String note;
    LocalDate bookingDate;
}
