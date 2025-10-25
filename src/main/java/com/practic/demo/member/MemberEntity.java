package com.practic.demo.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Builder
public class MemberEntity {

    private Long memberId;
    private String email;
    private String nickName;
    private String password;
    private String phoneNumber;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    /**
     * Member 생성하기
     *
     * @param email
     * @param nickName
     * @param password
     * @return
     */

    public static MemberEntity newMember(MemberCommand.CreateMember command
    ) {
        Objects.requireNonNull(command.getEmail());
        Objects.requireNonNull(command.getNickName());
        Objects.requireNonNull(command.getPassword());

        return MemberEntity.builder()
                .memberId(null)
                .email(command.getEmail())
                .nickName(command.getNickName())
                .password(command.getPassword())
                .phoneNumber(null)
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}