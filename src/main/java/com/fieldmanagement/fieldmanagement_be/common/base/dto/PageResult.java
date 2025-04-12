package com.fieldmanagement.fieldmanagement_be.common.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResult <T> {
    private T data;
    private MetaData metaData;
}
