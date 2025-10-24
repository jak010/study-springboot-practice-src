package com.practic.demo.member;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
