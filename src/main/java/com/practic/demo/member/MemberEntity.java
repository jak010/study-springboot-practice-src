package com.practic.demo.member;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberEntity {

    private Long memberId;
    private String nickName;
    private String email;
    private String password;
    private String phoneNumber;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
