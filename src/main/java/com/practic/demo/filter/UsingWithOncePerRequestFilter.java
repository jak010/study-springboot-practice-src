package com.practic.demo.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.practic.demo.contrib.CachedHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * Request & Response Logging 을 위한 Filter 적용
 */
@Slf4j
@Component
@Order(1)
public class UsingWithOncePerRequestFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        final CachedHttpServletRequest requestWrapper = new CachedHttpServletRequest(
            request);
        final ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
            response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            responseWrapper.copyBodyToResponse();
        }
    }


}
