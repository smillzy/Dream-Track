package com.appworks.school.dreamtrack;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    public static String creatJWTSign(String email, String jwtSignKey, Long jwtExpireTimeAsSec) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtExpireTimeAsSec)))
                .signWith(Keys.hmacShaKeyFor(jwtSignKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }

    public static Claims parseJWT(String token, String jwtSignKey) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSignKey.getBytes(StandardCharsets.UTF_8)))
                .parseClaimsJws(token)
                .getBody();
    }

//
//    public static Claims parseJWT(String token, String secret) {
//        return Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();
//    }
}
