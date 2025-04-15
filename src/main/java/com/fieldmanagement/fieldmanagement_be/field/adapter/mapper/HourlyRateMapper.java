package com.fieldmanagement.fieldmanagement_be.field.adapter.mapper;

import com.fieldmanagement.fieldmanagement_be.field.adapter.db.dto.TimeSlotEntityDTO;
import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.HourlyRateEntity;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.TimeSlotResponse;
import com.fieldmanagement.fieldmanagement_be.field.domain.dto.TimeSlotDTO;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.HourlyRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HourlyRateMapper {
    TimeSlotDTO toTimeSlotDTO(TimeSlotEntityDTO timeSlotEntityDTO);

    TimeSlotResponse toTimeSlotResponse(TimeSlotDTO timeSlotDTO);

    HourlyRate toHourlyRate(HourlyRateEntity hourlyRateEntity);
}
