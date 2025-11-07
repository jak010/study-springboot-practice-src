package com.practic.demo.config;

import com.practic.demo.filter.UsingWithOncePerRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<UsingWithOncePerRequestFilter> logFilter() {
        FilterRegistrationBean<UsingWithOncePerRequestFilter> filterRegistrationBean = new FilterRegistrationBean<UsingWithOncePerRequestFilter>();
        filterRegistrationBean.setFilter(new UsingWithOncePerRequestFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;

    }


}
