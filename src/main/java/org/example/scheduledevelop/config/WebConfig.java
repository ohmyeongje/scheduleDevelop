package org.example.scheduledevelop.config;

import jakarta.servlet.Filter;
import org.example.scheduledevelop.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter()); // 필터 등록
        filterRegistrationBean.setOrder(1); // 필터 실행 순서 지정
        filterRegistrationBean.addUrlPatterns("/*"); // 모든 요청에 대해 필터 적용
        return filterRegistrationBean;
    }
}

