package com.practic.demo.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * Request & Response Logging 을 위한 Filter 적용
 */
@Slf4j
public class LogFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        // ObjectMapper 생성
        ObjectMapper mapper = new ObjectMapper();

        try {

            // Request Logging
            log.info("[REQUEST-URL] : {}", request.getRequestURL());

            Map<String, String> headerMap = new HashMap<>();
            request.getHeaderNames().asIterator().forEachRemaining(
                headerName -> headerMap.put(headerName, request.getHeader(headerName))
            );

            // JSON 문자열 변환
            log.info("[REQUEST-HEADER] : {}", mapper.writeValueAsString(headerMap));
            log.info("[REQUEST-CONTENT]: {}",
                new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));

            filterChain.doFilter(requestWrapper, responseWrapper);

        } finally {
            log.info("response: {}",
                new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));
            responseWrapper.copyBodyToResponse();
        }


    }
}
