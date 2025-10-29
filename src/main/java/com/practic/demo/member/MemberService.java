package com.practic.demo.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface MemberService {

    /**
     * [MEM_01] 회원 가입
     *
     * @param command - 회원 가입 요청 커맨드 (이름, 닉네임, 비밀번호 포함)
     * @return - 등록된 회원 정보 (MemberEntity)
     * @throws com.practic.demo.member.exceptions.MemberDuplicated - 닉네임이 중복될 경우 발생
     */
    MemberEntity registerMember(MemberCommand.CreateMemberCommand command);

    /**
     * [MEM_02] 회원 정보 조회 (단건)
     * 특정 회원 식별코드(ID)를 사용하여 회원 정보를 조회합니다.
     *
     * @param memberId - 조회할 회원의 식별코드
     * @return - 조회된 회원 정보 (MemberEntity)
     * @throws com.practic.demo.member.exceptions.MemberNotFound - 해당 ID의 회원을 찾을 수 없을 경우 발생
     */
    MemberEntity getMember(Long memberId);

    /**
     * [MEM_03] 회원 정보 조회 (목록)
     * 여러 회원 식별코드(ID) 목록을 사용하여 회원 정보를 조회합니다.
     * - ToDo, 25.10.26 -  Paging 구현
     *
     * @param memberIds - 조회할 회원 식별코드 목록
     * @return - 조회된 회원 정보 목록 (List<MemberEntity>)
     */
    List<MemberEntity> getMembers(List<Integer> memberIds);

    /**
     * [MEM_04] 회원 상태 변경
     * 특정 회원의 상태(예: 활성, 비활성, 탈퇴)를 변경합니다.
     *
     * @param memberId - 상태를 변경할 회원의 식별코드
     * @param status   - 변경할 새로운 회원 상태
     * @return - 상태가 변경된 회원 정보 (MemberEntity)
     * @throws com.practic.demo.member.exceptions.MemberNotFound         - 해당 ID의 회원을 찾을 수 없을 경우 발생
     * @throws com.practic.demo.member.exceptions.MemberStatusAlreadySet - 이미 요청된 상태로 설정되어 있을 경우 발생
     * @throws com.practic.demo.member.exceptions.MemberStatusNotAllowed - 허용되지 않는 상태 변경일 경우 발생
     */
    MemberEntity updateMemberStatus(Long memberId, MemberStatus status);


    /**
     * [MEM_06] 닉네임 중복 체크
     * 주어진 닉네임이 이미 사용 중인지 확인합니다.
     *
     * @param nickname - 중복 체크할 닉네임
     * @return - 중복 여부 (true: 중복, false: 중복 아님)
     */
    boolean isNicknameDuplicate(String nickname);

    /**
     * [MEM_05] 이메일 중복 체크
     * 주어진 이메일이 이미 사용 중인지 확인합니다.
     *
     * @param email - 중복 체크할 이메일
     * @return - 중복 여부 (true: 중복, false: 중복 아님)
     */
    boolean isEmailDuplicate(String email);

    /**
     * [MEM_07] 회원 탈퇴
     * 특정 회원 식별코드(ID)를 사용하여 회원을 탈퇴 처리합니다.
     *
     * @param memberId - 탈퇴할 회원의 식별코드
     * @throws com.practic.demo.member.exceptions.MemberNotFound - 해당 ID의 회원을 찾을 수 없을 경우 발생
     */
    void deleteMember(Long memberId);

    /**
     * [MEM_07] 회원 정보 수정
     * 특정 회원의 정보를 수정합니다.
     *
     * @param memberId - 정보를 수정할 회원의 식별코드
     * @param command  - 회원 정보 수정 요청 커맨드 (닉네임, 이메일, 전화번호 포함)
     * @return - 수정된 회원 정보 (MemberEntity)
     * @throws com.practic.demo.member.exceptions.MemberNotFound   - 해당 ID의 회원을 찾을 수 없을 경우 발생
     * @throws com.practic.demo.member.exceptions.MemberDuplicated - 닉네임 또는 이메일이 중복될 경우 발생
     */
    MemberEntity updateMemberInfo(Long memberId, MemberCommand.UpdateMemberInfoCommand command);

    /**
     * [MEM_08] 비밀번호 변경
     * 특정 회원의 비밀번호를 새로운 비밀번호로 변경합니다.
     *
     * @param memberId - 비밀번호를 변경할 회원의 식별코드
     * @param password - 변경할 새로운 비밀번호
     * @throws com.practic.demo.member.exceptions.MemberNotFound                 - 해당 ID의 회원을 찾을 수 없을 경우 발생
     * @throws com.practic.demo.member.exceptions.MemberPasswordNotEquals        - 현재 비밀번호와 새 비밀번호가 일치하지 않을 경우 발생
     * @throws com.practic.demo.member.exceptions.MemberPasswordIsOriginPassword - 새 비밀번호가 기존 비밀번호와 동일할 경우 발생
     */
    void changePassword(Long memberId, String password);

    /**
     * [MEM_09] 비밀번호 초기화
     * 특정 회원의 비밀번호를 초기 비밀번호로 재설정합니다.
     *
     * @param memberId - 비밀번호를 초기화할 회원의 식별코드
     * @throws com.practic.demo.member.exceptions.MemberNotFound - 해당 ID의 회원을 찾을 수 없을 경우 발생
     */
    void resetPassword(Long memberId);

    /**
     * [SRC_01] 상태별 회원 조회
     * 특정 상태의 회원 목록을 페이징하여 조회합니다.
     *
     * @param status   - 조회할 회원 상태
     * @param pageable - 페이징 정보
     * @return - 페이징된 회원 정보 목록 (Page<MemberEntity>)
     */
    Page<MemberEntity> getMembersByStatus(MemberStatus status, Pageable pageable);


    /**
     * [SRC_03] 가입일 기준 조회
     */
    List<MemberEntity> getMembersByJoinDate(LocalDate start, LocalDate end);
}
