package com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.request;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.SearchRequest;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.FieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class FieldFilterRequest extends SearchRequest {
    private String location;
    private Float startPrice;
    private Float endPrice;
    private Float startTime;
    private Float endTime;
    private List<FieldTypeEnum> types;
}
