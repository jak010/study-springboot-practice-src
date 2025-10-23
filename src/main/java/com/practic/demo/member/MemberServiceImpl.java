package com.practic.demo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberEntity registerMember(MemberEntity memberEntity) {
        return null;
    }

    @Override
    public MemberEntity getMemberById(Long memberId) {
        Optional<MemberEntity> memberEntity = Optional.ofNullable(memberRepository.findByMemberId(memberId));
        return memberEntity.orElseThrow(RuntimeException::new);
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
