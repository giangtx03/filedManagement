package com.fieldmanagement.commom.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationRequest {
    @NotNull
    @Min(1)
    private int currentPage;

    @NotNull
    @Min(1)
    @Max(500)
    private int pageSize;

    public PaginationRequest(){
        this.currentPage = 1;
        this.pageSize = 10;
    }
}
