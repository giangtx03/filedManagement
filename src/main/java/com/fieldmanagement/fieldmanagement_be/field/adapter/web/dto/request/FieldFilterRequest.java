package com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.request;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.SearchRequest;
import com.fieldmanagement.fieldmanagement_be.field.domain.model.FieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class FieldFilterRequest extends SearchRequest {
    private String location;
    private Float startPrice;
    private Float endPrice;
    private LocalTime fromTime;
    private LocalTime toTime;
    private List<FieldTypeEnum> types;
}
