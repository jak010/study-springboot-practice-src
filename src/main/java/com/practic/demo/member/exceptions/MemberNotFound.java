package com.practic.demo.member.exceptions;

public class MemberNotFound extends RuntimeException {

    private final Long statusCode = 400L;
    private final String message = "사용자를 찾을 수 없음";


    @Override
    public String toString() {
        return super.toString();
    }
}
