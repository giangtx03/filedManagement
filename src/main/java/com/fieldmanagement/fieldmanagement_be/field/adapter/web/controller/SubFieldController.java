package com.fieldmanagement.fieldmanagement_be.field.adapter.web.controller;

import com.fieldmanagement.fieldmanagement_be.common.base.builder.ResponseBuilder;
import com.fieldmanagement.fieldmanagement_be.common.base.dto.ResponseDto;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.StatusCodeEnum;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.ScheduleResponse;
import com.fieldmanagement.fieldmanagement_be.field.usecase.FieldScheduleUseCase;
import com.fieldmanagement.fieldmanagement_be.infra.language.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/sub-fields")
public class SubFieldController {
    private final FieldScheduleUseCase fieldScheduleUseCase;
    private final LanguageService languageService;

    @GetMapping("/{subFieldId}/schedules")
    public ResponseEntity<ResponseDto<List<ScheduleResponse>>> getSchedules(
        @PathVariable("subFieldId") String subFieldId,
        @RequestParam("startDate") LocalDate startDate,
        @RequestParam("endDate") LocalDate endDate
    ) {
        List<ScheduleResponse> responses = fieldScheduleUseCase.getSchedules(
                subFieldId, startDate, endDate
        );

        StatusCodeEnum codeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<List<ScheduleResponse>> response = ResponseBuilder.okResponse(
                codeEnum.code,
                languageService.getMessage(codeEnum.message),
                responses
        );

        return ResponseEntity
                .status(codeEnum.httpStatusCode)
                .body(response);
    }
}
