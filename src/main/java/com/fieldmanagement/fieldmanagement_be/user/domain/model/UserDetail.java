package com.fieldmanagement.fieldmanagement_be.user.domain.model;

import com.fieldmanagement.fieldmanagement_be.common.base.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserDetail extends BaseModel {
    String id;
    String firstName;
    String lastName;
    String avatar;
    LocalDate dob;
    String phoneNumber;
    String address;

    public String getFullName() {
        return Stream.of(this.firstName, this.lastName)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" "));
    }
}
