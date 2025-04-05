package com.fieldmanagement.commom.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailBaseModel extends BaseModel {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "first_name", length = 64, nullable = false)
    String firstName;

    @Column(name = "last_name", length = 64)
    String lastName;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "dob")
    LocalDate dob;

    @Column(name = "phone_number", length = 16)
    String phoneNumber;

    @Column(name = "address")
    String address;
}
