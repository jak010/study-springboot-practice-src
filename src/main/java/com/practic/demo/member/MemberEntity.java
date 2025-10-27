package com.practic.demo.member;

import com.practic.demo.member.exceptions.MemberStatusAlreadySet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
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


    public static MemberEntity newMember(MemberCommand.CreateMemberCommand command
    ) {
        Objects.requireNonNull(command.getEmail());
        Objects.requireNonNull(command.getNickName());
        Objects.requireNonNull(command.getPassword());

        MemberEntity newMemberEntity = new MemberEntity();
        newMemberEntity.setMemberId(null);
        newMemberEntity.setEmail(command.getEmail());
        newMemberEntity.setNickName(command.getNickName());
        newMemberEntity.setPassword(command.getPassword());
        newMemberEntity.setPhoneNumber(null);
        newMemberEntity.setStatus("ACTIVE");
        newMemberEntity.setCreatedAt(LocalDateTime.now());
        newMemberEntity.setUpdatedAt(LocalDateTime.now());

        return newMemberEntity;
    }

    public boolean statusCompare(MemberStatus newStatus) {
        return status.equals(newStatus.getStatus());

    }

}