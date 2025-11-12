package com.practic.demo.config;

//import com.practic.demo.filter.UsingWithOncePerRequestFilter;

import com.practic.demo.filter.UsintCachingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<UsintCachingFilter> logFilter() {
        FilterRegistrationBean<UsintCachingFilter> filterRegistrationBean = new FilterRegistrationBean<UsintCachingFilter>();
        filterRegistrationBean.setFilter(new UsintCachingFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;

    }


}
