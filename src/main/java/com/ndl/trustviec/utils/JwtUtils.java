package com.ndl.trustviec.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.ndl.trustviec.dto.JwtTokenGenerate;
import com.ndl.trustviec.dto.JwtTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtils {
    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expire-time-access-token}")
    private long accessTokenExpireTime;

    @Value("${jwt.token.expire-time-refresh-token}")
    private long refreshTokenExpireTime;

    private final String ISSUER = "one-man-work";

    public JwtTokenResponse generateToken(JwtTokenGenerate jwtTokenGenerate) {
        Date now = new Date();
        Date accessTokenExpiryDate = new Date(now.getTime() + accessTokenExpireTime);
        Date refreshTokenExpiryDate = new Date(now.getTime() + refreshTokenExpireTime);
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String accessToken = JWT.create()
                .withSubject(jwtTokenGenerate.getCif())
                .withClaim("cif", jwtTokenGenerate.getCif())
                .withClaim("enterpriseId", jwtTokenGenerate.getEnterpriseId())
                .withClaim("email", jwtTokenGenerate.getEmail())
                .withClaim("roles", jwtTokenGenerate.getRoles())
                .withIssuedAt(now)
                .withIssuer(ISSUER)
                .withExpiresAt(accessTokenExpiryDate)
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(jwtTokenGenerate.getCif())
                .withClaim("email", jwtTokenGenerate.getEmail())
                .withClaim("isRefreshToken", true)
                .withIssuedAt(now)
                .withIssuer(ISSUER)
                .withExpiresAt(refreshTokenExpiryDate)
                .sign(algorithm);
        return new JwtTokenResponse(accessToken, refreshToken);
    }

    public String getSubject(String token) {
        return JWT.decode(token).getSubject();
    }

    public Claim getClaim(String token, String key) {
        return JWT.decode(token).getClaim(key);
    }

    public Map<String, Claim> getClaims(String token) {
        return JWT.decode(token).getClaims();
    }

    public boolean isRefreshToken(String token) {
        return getClaim(token, "isRefreshToken").asBoolean();
    }

    public boolean isTokenExpired(String token) {
        return JWT.decode(token).getExpiresAt().before(new Date());
    }

    public boolean isTokenValid(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            verifier.verify(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public boolean isRefreshTokenValid(String token) {
        return isTokenValid(token) && isRefreshToken(token);
    }

    public boolean isAccessTokenValid(String token) {
        return !isRefreshToken(token) && isTokenValid(token);
    }

    public UserDetails getUserDetails(String token) {
        Map<String, Claim> claims = getClaims(token);
        String username = getSubject(token); // hoặc claims.get("sub", String.class)
        List<String> roles = claims.get("roles").asList(String.class);
        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(username, "", authorities);
    }
}
