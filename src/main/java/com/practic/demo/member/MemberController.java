package com.practic.demo.member;


import com.practic.demo.contrib.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "MEMBER")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/member/{memberId}")
    public ApiResponse<MemberEntity> getMember(
        @Parameter(description = "회원 번호", required = true)
        @PathVariable("memberId") Long memberId
    ) {
        return ApiResponse.of(memberService.getMember(memberId))
            .getBody();
    }


    @Operation(summary = "member 목록조회")
    @GetMapping("/member")
    public ApiResponse<List<MemberEntity>> getMembers(

        @Parameter(description = "회원 번호", required = true)
        @RequestParam("memberIds") List<Integer> memberIds
    ) {
        return ApiResponse.of(memberService.getMembers(memberIds))
            .getBody();
    }

    @PostMapping("/member")
    public MemberEntity saveMember(
        @RequestBody MemberCommand.CreateMemberCommand createMember
    ) {
        return memberService.registerMember(createMember);
    }

    @PutMapping("/member/{memberId}/status")
    public MemberEntity updateMemberStatus(
        @PathVariable("memberId") Long memberId,
        @RequestBody MemberCommand.updateMemberChangeCommand status
    ) {
        return memberService.updateMemberStatus(memberId, status.getStatus());
    }


    @DeleteMapping("/member/{memberId}")
    public void deleteMember(
        @PathVariable("memberId") Long memberId
    ) {
        memberService.deleteMember(memberId);
    }

    @PutMapping("/member/{memberId}/password-change")
    public void updatePassword(
        @PathVariable("memberId") Long memberId,
        @RequestBody MemberCommand.updateMemberPasswordCommand command
    ) {
        memberService.changePassword(memberId, command.getPassword());
    }

    @PutMapping("/member/{memberId}/reset-password")
    public void resetPassword(
        @PathVariable("memberId") Long memberId
    ) {
        memberService.resetPassword(memberId);
    }

    @GetMapping("/member/joinedDate")
    public List<MemberEntity> getMemberJoinedDate(
        @RequestParam("start") LocalDate start,
        @RequestParam("end") LocalDate end
    ) {
        return memberService.getMembersByJoinDate(start, end);
    }


}
