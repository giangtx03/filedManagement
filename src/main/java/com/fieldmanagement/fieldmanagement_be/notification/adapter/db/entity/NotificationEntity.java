package com.fieldmanagement.fieldmanagement_be.notification.adapter.db.entity;

import com.fieldmanagement.fieldmanagement_be.common.base.entity.BaseEntity;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.NotificationTypeEnum;
import com.fieldmanagement.fieldmanagement_be.user.adapter.db.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "Notification")
@Table(name = "notifications")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEntity extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "content", nullable = false)
    String content;

    @Column(name = "is_read")
    boolean isRead;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    NotificationTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    UserEntity user;
}
