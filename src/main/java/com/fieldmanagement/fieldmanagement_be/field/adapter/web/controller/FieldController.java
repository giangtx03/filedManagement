package com.fieldmanagement.fieldmanagement_be.field.adapter.web.controller;

import com.fieldmanagement.fieldmanagement_be.common.base.builder.ResponseBuilder;
import com.fieldmanagement.fieldmanagement_be.common.base.dto.PageResponseDto;
import com.fieldmanagement.fieldmanagement_be.common.base.dto.PageResult;
import com.fieldmanagement.fieldmanagement_be.common.base.dto.ResponseDto;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.StatusCodeEnum;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.request.FieldFilterRequest;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.FieldDetailResponse;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.FieldResponse;
import com.fieldmanagement.fieldmanagement_be.field.usecase.FieldUseCase;
import com.fieldmanagement.fieldmanagement_be.infra.language.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/fields")
public class FieldController {
    private final FieldUseCase fieldUseCase;
    private final LanguageService languageService;

    @GetMapping
    public ResponseEntity<PageResponseDto<List<FieldResponse>>> getAllFields(
            @ParameterObject FieldFilterRequest request
    ) {
        PageResult<List<FieldResponse>> result = fieldUseCase.getAllFields(request);

        StatusCodeEnum codeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        PageResponseDto<List<FieldResponse>> response = ResponseBuilder.okResponse(
                codeEnum.code,
                languageService.getMessage(codeEnum.message),
                result
        );

        return ResponseEntity
                .status(codeEnum.httpStatusCode)
                .body(response);
    }

    @GetMapping("/{urlSlug}")
    public ResponseEntity<ResponseDto<FieldDetailResponse>> getFieldDetailByUrlSlug(
            @PathVariable("urlSlug") String urlSlug
    ) {
        FieldDetailResponse result = fieldUseCase.getFieldDetail(urlSlug);

        StatusCodeEnum codeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<FieldDetailResponse> response = ResponseBuilder.okResponse(
                codeEnum.code,
                languageService.getMessage(codeEnum.message),
                result
        );

        return ResponseEntity
                .status(codeEnum.httpStatusCode)
                .body(response);
    }
}
