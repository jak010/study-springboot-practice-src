package com.practic.demo.member;


import com.practic.demo.contrib.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/member/{memberId}")
    public ApiResponse<MemberEntity> getMember(
            @PathVariable("memberId") Long memberId
    ) {
        return ApiResponse.of(memberService.getMember(memberId))
                .getBody();
    }


    @GetMapping("/member")
    public ApiResponse<List<MemberEntity>> getMembers(
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

}
