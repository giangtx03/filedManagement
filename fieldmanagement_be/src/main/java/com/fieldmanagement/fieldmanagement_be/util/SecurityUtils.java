package com.fieldmanagement.fieldmanagement_be.util;

import com.fieldmanagement.fieldmanagement_be.config.security.UserDetailsImpl;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {

    public UserModel getUserFromSecurity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) principal).getUser();
        }
        return null;
    }
}
