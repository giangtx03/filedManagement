package com.fieldmanagement.fieldmanagement_be.dao.repository;

import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserModel, String> {

    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);
}
