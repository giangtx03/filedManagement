package com.fieldmanagement.commom.model.builder;

import com.fieldmanagement.commom.model.dto.MetaData;
import com.fieldmanagement.commom.model.dto.PageResponseDto;
import com.fieldmanagement.commom.model.dto.ResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseBuilder {
    public ResponseDto<Void> okResponse(int code, String message){
        return ResponseDto.<Void>builder()
                .message(message)
                .statusCode(code)
                .build();
    }

    public <T> ResponseDto<T> okResponse(int code, String message, T data) {
        return ResponseDto.<T>builder()
                .message(message)
                .statusCode(code)
                .data(data)
                .build();
    }

    public <T> PageResponseDto<T> okResponse(int code, String message, T data, MetaData metaData) {
        return PageResponseDto.<T>builder()
                .message(message)
                .statusCode(code)
                .data(data)
                .metaData(metaData)
                .build();
    }

    public ResponseDto<Void> errorResponse(int code, String message){
        return ResponseDto.<Void>builder()
                .message(message)
                .statusCode(code)
                .build();
    }

    public <T> ResponseDto<T> errorResponse(int code, String message,T data) {
        return ResponseDto.<T>builder()
                .message(message)
                .statusCode(code)
                .data(data)
                .build();
    }
}
