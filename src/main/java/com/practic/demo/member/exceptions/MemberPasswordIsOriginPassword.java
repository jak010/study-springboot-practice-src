package com.practic.demo.member.exceptions;

import com.practic.demo.settings.DomainException;
import lombok.Getter;

@Getter
public class MemberPasswordIsOriginPassword extends DomainException {
    private static final Long code = 4001L;
    private static final String message = "이전 비밀번호와 같은 비밀번호임";


    public MemberPasswordIsOriginPassword() {
        super(code, message);
    }
}
