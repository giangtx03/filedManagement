package com.fieldmanagement.fieldmanagement_be.booking.domain.model;

import com.fieldmanagement.fieldmanagement_be.common.base.model.BaseModel;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.HourlyRate;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.SubField;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;
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
public class Booking extends BaseModel {
    String id;
    String note;
    LocalDate bookingDate;
    BookingStatusEnum status;
    User user;
    HourlyRate hourlyRate;
    SubField subField;
}
