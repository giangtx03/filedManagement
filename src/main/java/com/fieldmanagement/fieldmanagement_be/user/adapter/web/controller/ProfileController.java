package com.fieldmanagement.fieldmanagement_be.user.adapter.web.controller;

import com.fieldmanagement.fieldmanagement_be.common.base.builder.ResponseBuilder;
import com.fieldmanagement.fieldmanagement_be.common.base.dto.ResponseDto;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.StatusCodeEnum;
import com.fieldmanagement.fieldmanagement_be.infra.language.LanguageService;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.request.UpdateProfileRequest;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.response.UserResponse;
import com.fieldmanagement.fieldmanagement_be.user.usecase.ProfileUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users/profile")
public class ProfileController {
    private final ProfileUseCase profileUseCase;
    private final LanguageService languageService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<ResponseDto<UserResponse>> getMe() {
        UserResponse userResponse = profileUseCase.getMe();

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<UserResponse> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                userResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<UserResponse>> updateProfile(
            @Valid @ModelAttribute UpdateProfileRequest profileRequest
    ) throws IOException {
        UserResponse userResponse = profileUseCase.updateProfile(profileRequest);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<UserResponse> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                userResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> delete() {
        profileUseCase.deleteProfile();

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UserResponse>> getOtherProfile(
            @PathVariable String id
    ) {
        UserResponse userResponse = profileUseCase.getOtherProfile(id);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<UserResponse> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                userResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }
}
