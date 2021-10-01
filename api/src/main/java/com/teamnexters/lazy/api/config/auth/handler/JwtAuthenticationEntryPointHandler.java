package com.teamnexters.lazy.api.config.auth.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    /**
     * 인증되지 않은 사용자가 REST 리소스에 액세스 요청 시 호출
     *
     * @param request       예외처리가 발생한 요청
     * @param response      인증이 되지 않은 상태를 응답
     * @param authException 호출된 인증 Exception
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 로그인 페이지가 없기 때문에 401 코드 응답
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않은 사용자입니다.");
    }
}
