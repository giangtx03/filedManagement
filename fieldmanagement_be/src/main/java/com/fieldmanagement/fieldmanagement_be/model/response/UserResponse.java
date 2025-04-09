package com.fieldmanagement.fieldmanagement_be.model.response;

import com.fieldmanagement.fieldmanagement_be.config.file.ImageField;
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
    private String id;
    private String fullName;
    @ImageField
    private String avatar;
    private String address;
    private LocalDate dob;
    private String phoneNumber;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
