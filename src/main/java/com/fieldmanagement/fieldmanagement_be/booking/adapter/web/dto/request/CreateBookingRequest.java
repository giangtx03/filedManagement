package com.fieldmanagement.fieldmanagement_be.booking.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingRequest {
    @NotBlank
    private String subFieldId;
    @NotBlank
    private String hourlyRateId;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private LocalDate date;
    private String note;
}
