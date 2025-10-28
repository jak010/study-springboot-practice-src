package com.practic.demo.member.impl;

import com.practic.demo.config.JasyptConfig;
import com.practic.demo.member.*;
import com.practic.demo.member.exceptions.MemberDuplicated;
import com.practic.demo.member.exceptions.MemberNotFound;
import com.practic.demo.member.exceptions.MemberStatusAlreadySet;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    @Qualifier("aesEncryptor")
    private final StringEncryptor aesEncryptor;

    /**
     * [MEM_01] 회원 가입
     *
     * @param command - 회원 정보
     * @return - 등록된 회원 정보
     */
    @Override
    @Transactional
    public MemberEntity registerMember(MemberCommand.CreateMemberCommand command) {
        Optional<MemberEntity> isSavedMember = memberRepository.duplicateCheck(command.getEmail(), command.getNickName());
        if (isSavedMember.isPresent()) {
            throw new MemberDuplicated();
        }


        MemberEntity memberEntity = MemberEntity.newMemberBuilder()
                .email(command.getEmail())
                .nickName(command.getNickName())
                .password(aesEncryptor.encrypt(command.getPassword()))
                .build();

        return memberRepository.save(memberEntity);
    }

    /**
     * [MEM_02] 회원 정보 조회 (단건)
     *
     * @param memberId - 회원 식별코드
     * @return - 회원 정보
     */
    @Override
    @Transactional
    public MemberEntity getMemberById(Long memberId) {
        return memberRepository.findMemberById(memberId).orElseThrow(MemberNotFound::new);
    }

    /**
     * [MEM_03] 회원 정보 조회 (목록)
     * - ToDo, 25.10.26 -  Paging 구현
     *
     * @return - 회원 정보 목록
     */
    @Override
    @Transactional
    public List<MemberEntity> getAllMembers(List<Integer> memberIds) {
        return memberRepository.findMemberByIds(memberIds);
    }

    /**
     * [MEM_04] 회원 상태 변경
     *
     * @param memberId - 회원 식별코드
     * @param status   - 변경할 상태
     * @return - 변경된 회원 정보
     */
    @Override
    @Transactional
    public MemberEntity updateMemberStatus(Long memberId, MemberStatus status) {
        MemberEntity memberEntity = memberRepository.findMemberById(memberId).orElseThrow(MemberNotFound::new);

        if (memberEntity.statusCompare(status)) {
            throw new MemberStatusAlreadySet();
        }

        return memberRepository.updateMemberStatus(memberEntity, status.getStatus());
    }

    /**
     * [MEM_06] 닉네임 중복 체스트
     *
     * @param nickname - 닉네임
     * @return - 중복된 회원 정보
     */
    @Override
    @Transactional
    public MemberEntity isNicknameDuplicate(String nickname) {
        return memberRepository.findMemberByNickName(nickname).orElseThrow(MemberNotFound::new);
    }

    @Override
    @Transactional
    public boolean deleteMember(Long memberId) {
        return memberRepository.deleteMemberById(memberId);

    }
}