package com.practic.demo.member.exceptions;

import com.practic.demo.settings.DomainException;
import lombok.Getter;

@Getter
public class MemberStatusAlreadySet extends DomainException {


    private static final Long code = 4001L;
    private static final String message = "회원 상태가 이미 설정되어 있습니다.";

    public MemberStatusAlreadySet() {
        super(code, message);
    }
}
