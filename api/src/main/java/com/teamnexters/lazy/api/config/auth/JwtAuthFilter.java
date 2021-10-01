package com.teamnexters.lazy.api.config.auth;

import com.teamnexters.lazy.api.config.auth.jwt.JwtTokenProvider;
import com.teamnexters.lazy.api.service.MemberService;
import com.teamnexters.lazy.common.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class JwtAuthFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberService memberService;

    public JwtAuthFilter(JwtTokenProvider tokenProvider) {
        this.jwtTokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = ((HttpServletRequest)request).getHeader("Authorization");

        log.info(">>> Filter Token : {}", token);

        if (token != null && jwtTokenProvider.verifyToken(token)) {
            String email = jwtTokenProvider.getUid(token);

            Optional<Member> member = memberService.getOneMemberByEmail(email);

            Authentication auth = getAuthentication(member.get());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    public Authentication getAuthentication(Member member) {
        return new UsernamePasswordAuthenticationToken(member, "",
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}