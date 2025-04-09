package com.fieldmanagement.fieldmanagement_be.service;

import com.fieldmanagement.fieldmanagement_be.model.request.user.UpdateProfileRequest;
import com.fieldmanagement.fieldmanagement_be.model.response.UserResponse;

import java.io.IOException;

public interface UserService {
    UserResponse getMe();
    UserResponse updateProfile(UpdateProfileRequest profileRequest) throws IOException;
}
