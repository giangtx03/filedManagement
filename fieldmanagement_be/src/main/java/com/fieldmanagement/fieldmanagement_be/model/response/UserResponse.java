package com.fieldmanagement.fieldmanagement_be.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("address")
    private String address;
    @JsonProperty("dob")
    private LocalDate dob;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}
