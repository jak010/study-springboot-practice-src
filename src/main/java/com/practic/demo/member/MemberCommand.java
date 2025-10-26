package com.practic.demo.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberCommand {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateMemberCommand {
        private String email;
        private String password;
        private String nickName;


    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateMemberChangeCommand {
        private MemberStatus status;




    }

}
