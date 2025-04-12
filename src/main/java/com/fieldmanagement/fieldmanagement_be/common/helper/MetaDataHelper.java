package com.fieldmanagement.fieldmanagement_be.common.helper;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.MetaData;
import com.fieldmanagement.fieldmanagement_be.common.base.dto.PaginationRequest;
import org.springframework.data.domain.Page;

public class MetaDataHelper {
    public static <T> MetaData buildMetaData(Page<T> page, PaginationRequest request) {
        if (page == null || request == null) {
            throw new IllegalArgumentException("Page or request must not be null");
        }
        return MetaData.builder()
                .currentPage(request.getCurrentPage())
                .pageSize(request.getPageSize())
                .totalItems(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
    }
}
