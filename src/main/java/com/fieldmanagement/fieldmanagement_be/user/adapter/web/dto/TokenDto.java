package com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto;

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
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
