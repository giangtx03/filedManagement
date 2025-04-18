package com.fieldmanagement.fieldmanagement_be.infra.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fieldmanagement.fieldmanagement_be.FieldManagementApplication;
import com.fieldmanagement.fieldmanagement_be.infra.language.LanguageService;
import com.fieldmanagement.fieldmanagement_be.user.domain.dto.TokenDTO;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final LanguageService languageService;

    @Value("${jwt.accessKey}")
    private String accessKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;

    @Value("${jwt.expiration.time.access}")
    private long timeAccess;

    @Value("${jwt.expiration.time.refresh}")
    private long timeRefresh;

    public TokenDTO generateToken(User userModel) {
        String accessToken = generateToken(userModel, accessKey, timeAccess);
        String refreshToken = generateToken(userModel, refreshKey, timeRefresh);

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String generateToken(User userModel, String key, Long time) {
        Algorithm algorithmRefresh = Algorithm.HMAC256(key.getBytes());
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(userModel.getEmail())
                .withIssuer(FieldManagementApplication.class.getPackageName())
                .withIssuedAt(now)
                .withExpiresAt(now.plus(Duration.ofMillis(time)))
                .sign(algorithmRefresh);
    }

    public DecodedJWT decodeToken(String token, String key) {
        Algorithm algorithm = Algorithm.HMAC256(key);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(FieldManagementApplication.class.getPackageName())
                .build();

        return verifier.verify(token);
    }
}
