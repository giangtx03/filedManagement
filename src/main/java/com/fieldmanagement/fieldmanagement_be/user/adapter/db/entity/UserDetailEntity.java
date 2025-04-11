package com.fieldmanagement.fieldmanagement_be.user.adapter.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fieldmanagement.fieldmanagement_be.common.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "UserDetailModel")
@Table(name = "user_details")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailEntity extends BaseEntity {
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

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true)
    UserEntity user;
}
