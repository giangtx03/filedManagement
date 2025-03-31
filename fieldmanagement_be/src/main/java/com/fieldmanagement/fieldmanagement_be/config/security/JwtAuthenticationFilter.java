package com.fieldmanagement.fieldmanagement_be.config.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fieldmanagement.commom.exception.UserNotFoundException;
import com.fieldmanagement.commom.model.builder.ResponseBuilder;
import com.fieldmanagement.commom.model.constant.AuthConstant;
import com.fieldmanagement.commom.model.dto.ResponseDto;
import com.fieldmanagement.commom.model.enums.StatusCodeEnum;
import com.fieldmanagement.fieldmanagement_be.config.jwt.JwtProvider;
import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

                    if (!userDetails.isAccountNonLocked()) {
                        sendErrorResponse(response, StatusCodeEnum.USER_LOCKED);
                    }

                    if (!userDetails.isEnabled()) {
                        sendErrorResponse(response, StatusCodeEnum.USER_UNACTIVE);
                    }

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }catch (JWTVerificationException e){
            sendErrorResponse(response, StatusCodeEnum.JWT_VERIFICATION_ERROR);
        }catch (UserNotFoundException e) {
            sendErrorResponse(response, StatusCodeEnum.USER_NOT_FOUND);
        }
        catch (Exception e) {
            sendErrorResponse(response, StatusCodeEnum.SERVER_EXCEPTION);
        }
    }

    private String extractToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(AuthConstant.AUTHORIZATION);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(AuthConstant.BEARER_TOKEN)) {
            return headerAuth.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, StatusCodeEnum errorCode) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.httpStatusCode.value());

        ResponseDto<?> responseDto = ResponseBuilder.errorResponse(
                errorCode.code,
                languageService.getMessage(errorCode.message)
        );

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        response.flushBuffer();
    }
}
