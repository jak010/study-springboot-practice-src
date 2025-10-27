package com.practic.demo.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    MemberEntity save(MemberEntity memberEntity);

    Optional<MemberEntity> findMemberByEmail(String email);

    Optional<MemberEntity> findMemberById(Long memberId);

    List<MemberEntity> findByMemberIds(List<Integer> memberIds);


    MemberEntity updateMemberStatus(MemberEntity memberEntity, String status);

}
