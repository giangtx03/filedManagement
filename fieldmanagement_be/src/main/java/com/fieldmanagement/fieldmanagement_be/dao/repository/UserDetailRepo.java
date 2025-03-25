package com.fieldmanagement.fieldmanagement_be.dao.repository;

import com.fieldmanagement.fieldmanagement_be.model.dto.UserDetailDto;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDetailRepo extends JpaRepository<UserDetailModel, String> {
    @Query("""
            SELECT new com.fieldmanagement.fieldmanagement_be.model.dto.UserDetailDto(
                u
            )
            FROM UserDetailModel u
            WHERE u.user.id = :userId
            """)
    Optional<UserDetailDto> findByUserId(@Param("userId") String userId);
}
