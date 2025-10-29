package com.practic.demo.contrib;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> {

    private final Integer statusCode;
    private final T data;

    @Builder(builderMethodName = "defaultBuilder")
    public ApiResponse(Integer statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }


    public static <T> ResponseEntity<ApiResponse<T>> of(T data) {
        ApiResponse<T> body = ApiResponse.<T>defaultBuilder()
                .statusCode(200)
                .data(data)
                .build();

        return ResponseEntity.ok(body);
    }


}
