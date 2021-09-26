package com.teamnexters.lazy.api.config.auth.jwt;

import com.teamnexters.lazy.common.domain.member.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class JwtTokenProvider {

    private String secretKey;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * 토큰 생성
     */
    public Token createToken(String uid, Role role) {
        long tokenPeriod = 1000L * 60L * 10L * 3L; // 30분
        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L; // 3주

        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("role", role);

        Date now = new Date();
        return new Token(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + tokenPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact());
    }

    /**
     * 토큰에서 subject(uuid) 추출
     *
     * @param token 토큰 문자열
     * @return 해당 토큰의 subject
     */
    public String getUid(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * 유효한 토큰인지 검증
     *
     * @param token 토큰 문자열
     * @return 유효 여부 반환
     */
    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
