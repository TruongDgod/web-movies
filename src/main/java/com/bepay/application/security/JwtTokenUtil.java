package com.bepay.application.security;

import com.bepay.application.config.JWTConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Autowired
    JWTConfig jwtConfig;

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;


    public String generateToken(String username, String roleCode) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("roleCode", roleCode);
        return doGenerateToken(claims);
    }

    private String doGenerateToken(Map<String, Object> claims) {
        byte[] apiKeySecretBytes = jwtConfig.getSecretKey().getBytes();
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpired() * 1000))
                .signWith(signingKey, SIGNATURE_ALGORITHM)
                .compact();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = decodeJWT(token);
        return claimsResolver.apply(claims);
    }


    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean checkToken(String token) {
        Claims claims = decodeJWT(token);
        if (claims == null) return false;
        Date expiration = claims.getExpiration();
        return Objects.nonNull(expiration) && new Date().before(expiration);
    }

    public boolean checkToken(String token, String roleCode) {
        Claims claims = decodeJWT(token);
        if (claims == null) return false;
        if (!roleCode.equals(claims.get("roleCode"))) return false;
        Date expiration = claims.getExpiration();
        return Objects.nonNull(expiration) && expiration.after(new Date());
    }

    public Claims decodeJWT(String jwt) {
        Claims result;
        try {
            result = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecretKey().getBytes())
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            result = null;
        }
        return result;
    }
}
