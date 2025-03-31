package com.fieldmanagement.fieldmanagement_be.config.security;

import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import com.fieldmanagement.fieldmanagement_be.dao.repository.UserRepo;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;
    private final LanguageService languageService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserModel userModel = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(""));
        return UserDetailsImpl.builder()
                .user(userModel)
                .build();
    }
}
