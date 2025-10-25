package com.practic.demo.member;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/member/{memberId}")
    public ResponseEntity<MemberEntity> getMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok()
                .body(memberService.getMemberById(memberId));

    }


    @GetMapping("/member")
    public List<MemberEntity> getMembers(@RequestParam("memberIds") List<Integer> memberIds) {
        return memberService.getAllMembers(memberIds);
    }

    @PostMapping("/member")
    public MemberEntity saveMember(
            @RequestBody MemberCommand.CreateMember createMember

    ) {
        return memberService.registerMember(createMember);
    }

}
