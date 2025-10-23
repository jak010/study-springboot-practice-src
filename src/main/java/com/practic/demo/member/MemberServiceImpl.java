package com.practic.demo.member;

import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    @Override
    public MemberEntity registerMember(MemberEntity memberEntity) {
        return null;
    }

    @Override
    public Optional<MemberEntity> getMemberById(Long memberId) {
        return Optional.empty();
    }

    @Override
    public List<MemberEntity> getAllMembers() {
        return List.of();
    }

    @Override
    public MemberEntity updateMemberStatus(Long memberId, MemberStatus status) {
        return null;
    }
}
