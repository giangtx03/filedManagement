package com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class HourlyRateResponse extends BaseResponse {
    private String id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long price;
}
