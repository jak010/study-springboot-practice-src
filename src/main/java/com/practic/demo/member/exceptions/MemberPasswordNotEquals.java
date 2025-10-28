package com.practic.demo.member.exceptions;

import com.practic.demo.settings.DomainException;
import lombok.Getter;

@Getter
public class MemberPasswordNotEquals extends DomainException {

    private static final Long code = 4001L;
    private static final String message = "사용자 비밀번호 불일치";

    public MemberPasswordNotEquals() {
        super(code, message);
    }

}
