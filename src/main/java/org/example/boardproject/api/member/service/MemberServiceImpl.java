package org.example.boardproject.api.member.service;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.member.common.MemberRole;
import org.example.boardproject.api.member.common.ServiceHandler;
import org.example.boardproject.api.member.dto.addmemberdto.RequestAddMemberDto;
import org.example.boardproject.api.member.dto.addmemberdto.ResponseAddMemberDto;
import org.example.boardproject.api.member.dto.loginDto.RequestLoginDto;
import org.example.boardproject.api.member.dto.loginDto.ResponseLoginDto;
import org.example.boardproject.api.member.entity.Member;
import org.example.boardproject.api.member.repository.MemberRepository;
import org.example.boardproject.common.token.service.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

    @Service
    @RequiredArgsConstructor
    public class MemberServiceImpl implements MemberService{
        private final MemberRepository memberRepository;
        private final ServiceHandler serviceHandler;
        private final PasswordEncoder passwordEncoder;
        private final TokenService tokenService;


        @Override
        @Transactional
        public ResponseAddMemberDto addMemberService(RequestAddMemberDto requestAddMemberDto) {
        if(memberRepository.existsByEmail((requestAddMemberDto.getEmail()))) {
            throw new RuntimeException("중복 이메일로 가입할 수 없습니다..");
        }
        String codeNumber = serviceHandler.createCode();
        String pw = passwordEncoder.encode(codeNumber);
        Member member = new Member(requestAddMemberDto, pw, MemberRole.PARTICIPANT);
        memberRepository.save(member);
        return new ResponseAddMemberDto(member);
    }

    @Override
    @Transactional
    public ResponseLoginDto loginMemberService(RequestLoginDto requestloginDto) {
        String email = requestloginDto.getEmail();
        if(!memberRepository.existsByEmail((requestloginDto.getEmail()))) {
            throw new RuntimeException("가입 정보가 없습니다.");
        }
        return new ResponseLoginDto(tokenService.login(email));
    }

    @Override
    @Transactional
    public ResponseAddMemberDto patchMemberService(RequestAddMemberDto requestAddMemberDto) {
        return null;
    }

    @Override
    @Transactional
    public ResponseAddMemberDto removeMemberService(RequestAddMemberDto requestAddMemberDto) {
        return null;
    }
}
