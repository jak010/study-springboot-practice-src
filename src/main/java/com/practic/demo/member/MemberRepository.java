package com.practic.demo.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    MemberEntity updateMemberInfo(MemberEntity memberEntity);

    Page<MemberEntity> findMembersByStatus(MemberStatus status, Pageable pageable);

    boolean deleteMemberById(Long memberId);
}
