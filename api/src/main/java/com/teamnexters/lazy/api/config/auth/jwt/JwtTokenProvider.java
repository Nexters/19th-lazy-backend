package com.teamnexters.lazy.api.config.auth.jwt;

import com.teamnexters.lazy.api.service.MemberService;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.domain.member.Role;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@PropertySource("classpath:application-oauth.yml")
public class JwtTokenProvider {

    private final long TOKEN_VALID_MILLISECOND = 1000L * 60L * 10L * 3L; // 30분
    private final long REFRESH_TOKEN_VALID_MILLISECOND = 1000L * 60L * 60L * 24L * 30L * 3L; // 3주

    private final MemberService memberService;

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    public JwtTokenProvider(@Qualifier("memberService") MemberService memberService) {
        this.memberService = memberService;
    }

    @PostConstruct
    protected void init() {
        log.info(">>> secretKey : {}", secretKey);
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * 토큰 생성
     */
    public Token createToken(String uid, Role role) {

        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("role", role);

        Date now = new Date();
        return new Token(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + TOKEN_VALID_MILLISECOND))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_MILLISECOND))
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
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // 인증 성공시 SecurityContextHolder 에 저장할 Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        Member member = memberService.getOneMemberByEmail(this.getUid(token));
        return new UsernamePasswordAuthenticationToken(member, "", List.of(new SimpleGrantedAuthority("USER")));
    }
}
