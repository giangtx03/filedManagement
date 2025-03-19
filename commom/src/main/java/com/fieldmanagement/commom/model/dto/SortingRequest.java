package com.fieldmanagement.commom.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortingRequest {
    private String order;
    private String direction;

    public SortingRequest(){
        this.order = "updatedAt";
        this.direction = "asc";
    }
}
