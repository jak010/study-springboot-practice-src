package com.practic.demo.member.impl;

import com.practic.demo.member.*;
import com.practic.demo.member.exceptions.MemberNotFound;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberEntity registerMember(MemberCommand.CreateMember command) {

        // TODO, 25-10-26 : Duplicated 처리 필요

        MemberEntity memberEntity = MemberEntity.newMember(command);

        MemberEntity savedMember = memberRepository.save(memberEntity);

        return savedMember;
    }

    @Override
    public MemberEntity getMemberById(Long memberId) {
        return memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFound::new);
    }

    @Override
    public List<MemberEntity> getAllMembers(List<Integer> memberIds) {
        return memberRepository.findByMemberIds(memberIds);
    }

    @Override
    public MemberEntity updateMemberStatus(Long memberId, MemberStatus status) {
        return null;
    }
}
