package com.teamnexters.lazy.api.config.auth;

import com.teamnexters.lazy.api.config.auth.handler.JwtAccessDeniedHandler;
import com.teamnexters.lazy.api.config.auth.handler.JwtAuthenticationEntryPointHandler;
import com.teamnexters.lazy.api.config.auth.handler.OAuth2FailureHandler;
import com.teamnexters.lazy.api.config.auth.handler.OAuth2SuccessHandler;
import com.teamnexters.lazy.api.config.auth.jwt.JwtTokenProvider;
import com.teamnexters.lazy.common.domain.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final OAuth2FailureHandler failureHandler;
    private final JwtAuthenticationEntryPointHandler jwtAuthenticationEntryPointHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtTokenProvider tokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // REST API - 로그인 페이지 폼 X, csrf 보안 불필요, 토큰 기반 인증으로 세션 생성 X
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // URL 별 권한 관리
        http.authorizeRequests()
                .antMatchers(
                        // 누구나 접근 가능한 설정
                        "/", "/login/**",
                        "/css/**", "/images/**", "/js/**",
                        "/swagger-ui/**", "/swagger-ui.html",
                        "/api-docs/**",
                        "/token/**",
                        "/api/v1/oauth/**",
                        "/h2-console/**",
                        "/api/v1/oauth/sign-in/**"
                ).permitAll()
                .antMatchers(
                        // "USER"인 경우만 접근 가능
                        "/admin/**"
                ).hasRole(Role.USER.name())
                // 나머지 URL 요청은 무조건 인증
                .anyRequest().authenticated();

        // OAuth2 로그인 관련 처리
        http.oauth2Login()
                .userInfoEndpoint() // 로그인 성공 시, 사용자 정보 설정
                .userService(customOAuth2UserService) // 로그인 성공 후, 사용자를 다룰 서비스 등록
                .and()
                .successHandler(successHandler)
                .failureHandler(failureHandler);

        // 예외처리 (인증여부, 권한여부)
        http.exceptionHandling()
                // 인증되지 않은 사용자
                .authenticationEntryPoint(jwtAuthenticationEntryPointHandler)
                // 인증 o 권한 x
                .accessDeniedHandler(jwtAccessDeniedHandler);

        http.logout()
                .deleteCookies("JSESSIONID");

        http.addFilterBefore(new JwtAuthFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

}