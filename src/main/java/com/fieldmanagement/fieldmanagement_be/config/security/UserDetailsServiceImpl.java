package com.fieldmanagement.fieldmanagement_be.config.security;

import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;
import com.fieldmanagement.fieldmanagement_be.user.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(""));
        return UserDetailsImpl.builder()
                .user(user)
                .build();
    }
}
