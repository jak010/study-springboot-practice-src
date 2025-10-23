package com.practic.demo.member;

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

}
