package com.practic.demo.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.practic.demo.member.exceptions.MemberStatusNotAllowed;

public enum MemberStatus {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    BLOCKED("BLOCKED");

    private String status;

    MemberStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static MemberStatus from(String value) {
        try {
            return MemberStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new MemberStatusNotAllowed();
        }
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }


}
