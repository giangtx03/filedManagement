package com.fieldmanagement.fieldmanagement_be.config.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fieldmanagement.commom.model.constant.AuthConstant;
import com.fieldmanagement.fieldmanagement_be.config.jwt.JwtProvider;
import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final LanguageService languageService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String accessToken = extractToken(request);
            if (accessToken != null) {
                DecodedJWT decodeToken = jwtProvider.decodeToken(accessToken);
                String email = decodeToken.getSubject();

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    if (!userDetails.isAccountNonLocked() || !userDetails.isEnabled()) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Tài khoản bị khóa hoặc vô hiệu hóa");
                        return;
                    }

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            log.error("JWT Verification failed: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, languageService.getMessage("token.invalid"));
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Authentication failed");
        }
    }

    private String extractToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(AuthConstant.AUTHORIZATION);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(AuthConstant.BEARER_TOKEN)) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
