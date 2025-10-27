package com.practic.demo.member.impl;

import com.practic.demo.member.*;
import com.practic.demo.member.exceptions.MemberDuplicated;
import com.practic.demo.member.exceptions.MemberNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberEntity registerMember(MemberCommand.CreateMemberCommand command) {
        Optional<MemberEntity> existMember = memberRepository.findMemberByEmail(command.getEmail());

        if (existMember.isPresent()) {
            throw new MemberDuplicated();
        }


        MemberEntity memberEntity = MemberEntity.newMember(command);
        return memberRepository.save(memberEntity);
    }

    @Override
    public MemberEntity getMemberById(Long memberId) {
        return memberRepository.findMemberById(memberId).orElseThrow(MemberNotFound::new);
    }

    @Override
    public List<MemberEntity> getAllMembers(List<Integer> memberIds) {
        return memberRepository.findByMemberIds(memberIds);
    }

    @Override
    @Transactional
    public MemberEntity updateMemberStatus(Long memberId, MemberStatus status) {
        MemberEntity memberEntity = memberRepository.findMemberById(memberId)
                .orElseThrow(MemberNotFound::new);

        MemberEntity updatedMember = memberRepository.updateMemberStatus(memberEntity, status.getStatus());


        return updatedMember;
    }
}
