package com.fieldmanagement.fieldmanagement_be.field.adapter.db.entity;

import com.fieldmanagement.fieldmanagement_be.common.base.entity.BaseEntity;
import com.fieldmanagement.fieldmanagement_be.review.adapter.db.entity.ReviewEntity;
import com.fieldmanagement.fieldmanagement_be.user.adapter.db.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Entity(name = "Field")
@Table(name = "fields")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldEntity extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 64, updatable = false, nullable = false)
    String id;

    @Column(name = "name", nullable = false, length = 64)
    String name;

    @Column(name = "url_slug", nullable = false)
    String urlSlug;

    @Column(name = "location", nullable = false)
    String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false, updatable = false)
    UserEntity owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorites",
            joinColumns = @JoinColumn(name = "large_field_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"large_field_id", "user_id"})
    )
    List<UserEntity> users;

    @OneToMany(mappedBy = "largeField")
    List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "largeField")
    List<SubFieldEntity> smallFields;
}
