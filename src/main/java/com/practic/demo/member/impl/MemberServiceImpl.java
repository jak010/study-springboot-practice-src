package com.practic.demo.member.impl;

import com.practic.demo.member.*;
import com.practic.demo.member.exceptions.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);


    @Autowired
    @Qualifier("aesEncryptor")
    private final StringEncryptor aesEncryptor;

    @Autowired
    @Qualifier("desEncryptor")
    private final StringEncryptor desEncryptor;

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


    @Override
    @Transactional
    public void changePassword(Long memberId, String password) {
        MemberEntity memberEntity = memberRepository.findMemberById(memberId).orElseThrow(MemberNotFound::new);

        // 기존에 저장된 password가 복호화가 불가능한 경우 어떻게 대처할 수 있을까 ?
        try {
            String originPassword = desEncryptor.decrypt(memberEntity.getPassword());
            if (originPassword.equals(password)) {
                throw new MemberPasswordIsOriginPassword();
            }
        } catch (EncryptionOperationNotPossibleException e) { // 25-10-28: 암호화에 사용된 알고리즘이 다른 경우에도 해당 에러가 발생한다.
            log.warn("회원 ID {} 비밀번호 복호화 실패: {}", memberId, e.getMessage());
        }


        memberEntity.setPassword(desEncryptor.encrypt(password));
        memberRepository.updateMemberPassword(memberEntity);
        System.out.println("?>");
    }
}