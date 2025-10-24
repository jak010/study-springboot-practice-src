package com.practic.demo.settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practic.demo.member.exceptions.MemberNotFound;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DomainException extends RuntimeException {

    private Long code;
    private String message;

    public DomainException(Long code, String message) {
        this.code = code;
        this.message = message;
    }


    public String toPayload() throws JsonProcessingException {
        Map<String, Object> payload = new HashMap<>();
        payload.put("code", code);
        payload.put("message", message);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(payload);

    }
}
