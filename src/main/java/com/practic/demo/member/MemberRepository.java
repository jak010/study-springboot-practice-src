package com.practic.demo.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    MemberEntity save(MemberEntity memberEntity);

    Optional<MemberEntity> findByMemberId(Long memberId);

    List<MemberEntity> findByMemberIds(List<Integer> memberIds);

}
