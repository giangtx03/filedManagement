package com.fieldmanagement.fieldmanagement_be.model.dto;

import com.fieldmanagement.fieldmanagement_be.model.entity.UserDetailModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailDto extends UserDetailModel {
    public UserDetailDto(UserDetailModel userDetailModel) {
        mapBaseModel(userDetailModel);
    }

    private void mapBaseModel(UserDetailModel userDetailModel) {
        super.setId(userDetailModel.getId());
        super.setFirstName(userDetailModel.getFirstName());
        super.setLastName(userDetailModel.getLastName());
        super.setAvatar(userDetailModel.getAvatar());
        super.setAddress(userDetailModel.getAddress());
        super.setDob(userDetailModel.getDob());
        super.setPhoneNumber(userDetailModel.getPhoneNumber());
        super.setCreatedAt(userDetailModel.getCreatedAt());
        super.setUpdatedAt(userDetailModel.getUpdatedAt());
    }
}
