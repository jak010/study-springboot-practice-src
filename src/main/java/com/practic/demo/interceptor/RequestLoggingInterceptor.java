//package com.practic.demo.interceptor;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Slf4j
//public class RequestLoggingInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
//        Object handler) throws Exception {
//
////        ServletRequestAttributes attrs =
////            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
////        HttpServletRequest cachedRequest = Objects.requireNonNull(attrs)
////            .getRequest();
//
////        loggedRequestHeader(cachedRequest);
////        loggedRequestBody(cachedRequest);
//
//        return true;
//    }
//
//    private void loggedRequestHeader(HttpServletRequest cachedRequest)
//        throws JsonProcessingException {
//        Map<String, Object> hmap = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//
//        Enumeration<String> headerNames = cachedRequest.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            hmap.put(headerName, cachedRequest.getHeader(headerName));
//        }
//
//        log.info("Request Header : {}", mapper.writeValueAsString(hmap));
//    }
//    
//
//    private void loggedRequestBody(HttpServletRequest cachedRequest) throws IOException {
//        log.info("Request Body: {}",
//            IOUtils.toString(cachedRequest.getInputStream(), StandardCharsets.UTF_8));
//
//    }
//}
