package com.practic.demo.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    MemberEntity save(MemberEntity memberEntity);

    Optional<MemberEntity> findMemberByEmail(String email);

    Optional<MemberEntity> findMemberById(Long memberId);

    List<MemberEntity> findMemberByIds(List<Integer> memberIds);

    Optional<MemberEntity> findMemberByNickName(String nickName);

    Optional<MemberEntity> duplicateCheck(String email, String nickName);

    MemberEntity updateMemberStatus(MemberEntity memberEntity, String status);

    MemberEntity updateMemberPassword(MemberEntity memberEntity);

    boolean deleteMemberById(Long memberId);
}
