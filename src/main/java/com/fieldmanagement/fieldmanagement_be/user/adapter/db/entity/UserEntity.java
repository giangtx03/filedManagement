package com.fieldmanagement.fieldmanagement_be.user.adapter.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fieldmanagement.fieldmanagement_be.booking.adapter.db.entity.BookingEntity;
import com.fieldmanagement.fieldmanagement_be.common.base.entity.BaseEntity;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.ProviderEnum;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.RoleEnum;
import com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity.FieldEntity;
import com.fieldmanagement.fieldmanagement_be.notification.adapter.db.entity.NotificationEntity;
import com.fieldmanagement.fieldmanagement_be.review.adapter.db.entity.ReviewEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "UserModel")
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "email", length = 64, nullable = false)
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "is_locked", nullable = false)
    boolean locked;

    @Column(name = "is_active", nullable = false)
    boolean active;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role")
    RoleEnum role;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "provider")
    ProviderEnum provider;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    UserDetailEntity userDetail;

    @OneToMany(mappedBy = "owner")
    List<FieldEntity> field;

    @OneToMany(mappedBy = "user")
    List<BookingEntity> bookings;

    @OneToMany(mappedBy = "user")
    List<NotificationEntity> notifications;

    @OneToMany(mappedBy = "user")
    List<ReviewEntity> reviews;

    @ManyToMany(mappedBy = "users")
    List<FieldEntity> favoriteFields;
}