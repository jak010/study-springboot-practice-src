package com.practic.demo.member;

import java.util.List;

public interface MemberService {

    /**
     * [MEM_01] 회원 가입
     *
     * @param memberEntity - 회원 정보
     * @return - 등록된 회원 정보
     */
    MemberEntity registerMember(MemberCommand.CreateMemberCommand command);

    /**
     * [MEM_02] 회원 정보 조회 (단건)
     *
     * @param memberId - 회원 식별코드
     * @return - 회원 정보
     */
    MemberEntity getMemberById(Long memberId);

    /**
     * [MEM_03] 회원 정보 조회 (목록)
     * - ToDo, 25.10.26 -  Paging 구현
     *
     * @return - 회원 정보 목록
     */
    List<MemberEntity> getAllMembers(List<Integer> memberIds);

    /**
     * [MEM_04] 회원 상태 변경
     *
     * @param memberId - 회원 식별코드
     * @param status   - 변경할 상태
     * @return - 변경된 회원 정보
     */
    MemberEntity updateMemberStatus(Long memberId, MemberStatus status);


    /**
     * [MEM_06] 닉네임 중복 체크
     */
    MemberEntity isNicknameDuplicate(String nickname);

}
