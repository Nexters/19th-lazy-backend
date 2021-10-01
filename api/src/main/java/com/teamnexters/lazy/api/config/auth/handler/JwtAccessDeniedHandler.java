package com.teamnexters.lazy.api.config.auth.handler;
import com.teamnexters.lazy.common.error.ErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 권한이 허가되지 않은 경우 호출
     *
     * @param request               AccessDeniedException 원인 요청
     * @param response              접근 실패 원인을 파악할 수 있는 응답
     * @param accessDeniedException 호출된 Exception
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        // 리디렉션할 오류페이지가 없기 때문에 403 코드 응답
        response.sendError(HttpServletResponse.SC_FORBIDDEN, ErrorCode.HANDLE_ACCESS_DENIED.getMessage());
    }

}
