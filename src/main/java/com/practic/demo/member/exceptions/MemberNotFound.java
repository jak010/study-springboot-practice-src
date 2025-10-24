package com.practic.demo.member.exceptions;

import com.practic.demo.settings.DomainException;
import lombok.Getter;

@Getter
public class MemberNotFound extends DomainException {


    private static final Long code = 4001L;
    private static final String message = "사용자를 찾을 수 없음";


    public MemberNotFound() {
        super(code, message);
    }
}
