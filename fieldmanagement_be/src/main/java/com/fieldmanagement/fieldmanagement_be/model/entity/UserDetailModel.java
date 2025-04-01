package com.fieldmanagement.fieldmanagement_be.model.entity;

import com.fieldmanagement.commom.model.entity.UserDetailBaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "UserDetailModel")
@Table(name = "user_details")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailModel extends UserDetailBaseModel {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true)
    UserModel user;

    public String getFullName() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }
}
