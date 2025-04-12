package com.fieldmanagement.fieldmanagement_be.common.base.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest extends SortingAndPaginationRequest {
    private String keyword;
}
