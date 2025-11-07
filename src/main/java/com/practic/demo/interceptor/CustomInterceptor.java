package com.practic.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

public class CustomInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        System.out.println("=========");

        ServletRequestAttributes attrs =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request2 = Objects.requireNonNull(attrs).getRequest();
        String userAgent = request2.getHeader("User-Agent");

        System.out.println("Interceptor Read");
        System.out.println(
            IOUtils.toString(request2.getInputStream(), StandardCharsets.UTF_8)
        );

        System.out.println("=========");

        return true;
    }
}
