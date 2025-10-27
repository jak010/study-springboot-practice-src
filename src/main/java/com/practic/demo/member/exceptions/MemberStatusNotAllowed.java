package com.practic.demo.member.exceptions;

import com.practic.demo.settings.DomainException;
import lombok.Getter;

@Getter
public class MemberStatusNotAllowed extends DomainException  {

    private static final Long code = 4001L;
    private static final String message = "허용되지 않는 status 값 : [ACTIVE, INACTIVE, BLOCKED]";

    public MemberStatusNotAllowed() {
        super(code, message);
    }
}
