package com.fieldmanagement.commom.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortingAndPaginationRequest extends PaginationRequest {
    private String order;
    private String direction;

    public SortingAndPaginationRequest(){
        super();
        this.order = "updatedAt";
        this.direction = "asc";
    }
}
