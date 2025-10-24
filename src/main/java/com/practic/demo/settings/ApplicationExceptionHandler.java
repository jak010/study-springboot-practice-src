package com.practic.demo.settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practic.demo.contrib.ResponseBody;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<String> domainExceptionHandler(DomainException e) {

        String body = "";
        try {
            body = e.toPayload();
        } catch (JsonProcessingException exp) {
            body = "Json Processing Error";
        }

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}
