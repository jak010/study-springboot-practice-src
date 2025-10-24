package com.practic.demo.member;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
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
    public MemberEntity getMember(@PathVariable("memberId") Long memberId) {
        return memberService.getMemberById(memberId);

    }


    @GetMapping("/member")
    public List<MemberEntity> getMembers(@RequestParam("memberIds") List<Integer> memberIds) {
        return memberService.getAllMembers(memberIds);
    }

}
