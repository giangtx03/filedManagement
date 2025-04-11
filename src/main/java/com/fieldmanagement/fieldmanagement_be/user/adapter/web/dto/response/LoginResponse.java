package com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserResponse user;
}
