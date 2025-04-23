package com.fieldmanagement.fieldmanagement_be.payment.adapter.web.controller;

import com.fieldmanagement.fieldmanagement_be.common.base.builder.ResponseBuilder;
import com.fieldmanagement.fieldmanagement_be.common.base.dto.ResponseDto;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.StatusCodeEnum;
import com.fieldmanagement.fieldmanagement_be.infra.language.LanguageService;
import com.fieldmanagement.fieldmanagement_be.payment.adapter.web.dto.request.CreatePaymentRequest;
import com.fieldmanagement.fieldmanagement_be.payment.usecase.PayUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/payments/pay")
public class PayController {
    private final PayUseCase payUseCase;
    private final LanguageService languageService;

    @PostMapping
    public ResponseEntity<ResponseDto<String>> createPay(
            @ParameterObject @Valid CreatePaymentRequest request,
            HttpServletRequest httpServletRequest
    ) throws UnsupportedEncodingException {
        String result = payUseCase.createPayment(request, httpServletRequest);
        StatusCodeEnum codeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<String> response = ResponseBuilder.okResponse(
                codeEnum.code,
                languageService.getMessage(codeEnum.message),
                result
        );

        return ResponseEntity
                .status(codeEnum.httpStatusCode)
                .body(response);
    }

    @GetMapping("/return")
    public ResponseEntity<ResponseDto<Void>> callback(
            @RequestParam Map<String, String> params
    ) {
        payUseCase.callback(params);
        StatusCodeEnum codeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<Void> response = ResponseBuilder.okResponse(
                codeEnum.code,
                languageService.getMessage(codeEnum.message)
        );

        return ResponseEntity
                .status(codeEnum.httpStatusCode)
                .body(response);
    }

}
