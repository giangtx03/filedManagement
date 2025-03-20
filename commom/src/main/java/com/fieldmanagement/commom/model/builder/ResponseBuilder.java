package com.fieldmanagement.commom.model.builder;

import com.fieldmanagement.commom.model.dto.MetaData;
import com.fieldmanagement.commom.model.dto.PageResponseDto;
import com.fieldmanagement.commom.model.dto.ResponseDto;
import com.fieldmanagement.commom.model.enums.StatusCodeEnum;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseBuilder {
    public ResponseDto<Void> okResponse(String message, StatusCodeEnum statusCode){
        return ResponseDto.<Void>builder()
                .message(message)
                .statusCode(statusCode.value)
                .build();
    }

    public <T> ResponseDto<T> okResponse(String message,T data, StatusCodeEnum statusCode) {
        return ResponseDto.<T>builder()
                .message(message)
                .statusCode(statusCode.value)
                .data(data)
                .build();
    }

    public <T> PageResponseDto<T> okResponse(String message, T data, MetaData metaData, StatusCodeEnum statusCode) {
        return PageResponseDto.<T>builder()
                .message(message)
                .statusCode(statusCode.value)
                .data(data)
                .metaData(metaData)
                .build();
    }

    public ResponseDto<Void> errorResponse(String message, StatusCodeEnum statusCode){
        return ResponseDto.<Void>builder()
                .message(message)
                .statusCode(statusCode.value)
                .build();
    }

    public <T> ResponseDto<T> errorResponse(String message,T data, StatusCodeEnum statusCode) {
        return ResponseDto.<T>builder()
                .message(message)
                .statusCode(statusCode.value)
                .data(data)
                .build();
    }
}
