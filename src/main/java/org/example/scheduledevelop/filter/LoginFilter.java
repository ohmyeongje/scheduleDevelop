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
    private static final String[] WHITE_LIST = {"/", "/user/signup", "/login", "/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURL = httpRequest.getRequestURI();

        log.info("로그인 필터 실행: {}", requestURL);

        // 1️⃣ 인증이 필요 없는 요청(화이트리스트)은 필터를 거치지 않고 통과
        if (isWhiteList(requestURL)) {
            chain.doFilter(request, response);
            return;
        }

        // 2️⃣ 로그인 검증: 세션에서 사용자 정보 확인
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            log.warn("로그인되지 않은 사용자 요청: {}", requestURL);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return;
        }

        // 3️⃣ 정상 로그인된 사용자 요청 -> 필터 통과
        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURL) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURL);
    }
}

