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

    @Builder(builderMethodName = "newMemberBuilder")
    public MemberEntity(String email, String nickName, String password) {
        Objects.requireNonNull(email);
        Objects.requireNonNull(nickName);
        Objects.requireNonNull(password);

        this.memberId = null;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = null;
        this.status = "ACTIVE";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public boolean statusCompare(MemberStatus newStatus) {
        return status.equals(newStatus.getStatus());

    }

}