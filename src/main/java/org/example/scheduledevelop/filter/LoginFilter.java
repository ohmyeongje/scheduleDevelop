package org.example.scheduledevelop.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
@Slf4j
public class LoginFilter implements Filter {
    private static final String[] WHITE_LIST = {"/", "/users/signup", "/users/login", "/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURL = httpRequest.getRequestURI();

        log.info(" 로그인 필터 실행: {}", requestURL);

        if (isWhiteList(requestURL)) {
            log.info(" 화이트리스트 URL, 필터 통과: {}", requestURL);
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);

        if (session == null) {
            log.warn(" 로그인되지 않은 사용자 요청 (세션 없음): {}", requestURL);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return;
        }

        Object user = session.getAttribute("user");
        log.info(" 세션 ID: {}", session.getId()); // 세션 ID 확인
        log.info(" 세션에 저장된 사용자: {}", user); // 세션에 저장된 데이터 확인

        if (user == null) {
            log.warn(" 로그인되지 않은 사용자 요청 (세션 존재하지만 user 없음): {}", requestURL);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return;
        }

        log.info("✅ 인증된 요청: {} (사용자: {})", requestURL, user);
        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURL) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURL);
    }
}
