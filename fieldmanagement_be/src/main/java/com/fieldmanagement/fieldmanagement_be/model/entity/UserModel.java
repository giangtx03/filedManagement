package com.fieldmanagement.fieldmanagement_be.model.entity;

import com.fieldmanagement.commom.model.entity.UserBaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "UserModel")
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserModel extends UserBaseModel {
    @OneToOne(mappedBy = "user")
    UserDetailModel userDetail;

    @OneToMany(mappedBy = "owner")
    List<LargeFieldModel> largeField;

    @OneToMany(mappedBy = "user")
    List<BookingModel> bookings;

    @OneToMany(mappedBy = "user")
    List<NotificationModel> notifications;

    @OneToMany(mappedBy = "user")
    List<ReviewModel> reviews;

    @ManyToMany(mappedBy = "users")
    List<LargeFieldModel> favoriteFields;
}
