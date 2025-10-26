package com.practic.demo.member.exceptions;

import com.practic.demo.settings.DomainException;
import lombok.Getter;

@Getter
public class MemberDuplicated extends DomainException {

    private static final Long code = 4001L;
    private static final String message = "사용자 이메일이 중복됨";


    public MemberDuplicated() {
        super(code, message);
    }

}
