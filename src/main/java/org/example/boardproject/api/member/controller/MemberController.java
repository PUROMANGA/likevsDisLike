package org.example.boardproject.api.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.member.dto.addmemberdto.RequestAddMemberDto;
import org.example.boardproject.api.member.dto.addmemberdto.ResponseAddMemberDto;
import org.example.boardproject.api.member.dto.loginDto.RequestLoginDto;
import org.example.boardproject.api.member.dto.loginDto.ResponseLoginDto;
import org.example.boardproject.api.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseAddMemberDto> addMember(@RequestBody @Valid RequestAddMemberDto requestAddMemberDto) {
        return ResponseEntity.ok(memberService.addMemberService(requestAddMemberDto));
    }
    //로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDto> loginMember(@RequestBody @Valid RequestLoginDto requestloginDto) {
        return ResponseEntity.ok(memberService.loginMemberService(requestloginDto));
    }
}
