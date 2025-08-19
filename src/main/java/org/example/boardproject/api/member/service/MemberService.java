package org.example.boardproject.api.member.service;

import jakarta.validation.Valid;
import org.example.boardproject.api.member.dto.addmemberdto.RequestAddMemberDto;
import org.example.boardproject.api.member.dto.addmemberdto.ResponseAddMemberDto;
import org.example.boardproject.api.member.dto.loginDto.RequestLoginDto;
import org.example.boardproject.api.member.dto.loginDto.ResponseLoginDto;

public interface MemberService {
    ResponseAddMemberDto addMemberService(RequestAddMemberDto requestAddMemberDto);
    ResponseLoginDto loginMemberService(RequestLoginDto requestloginDto);
    ResponseAddMemberDto patchMemberService(RequestAddMemberDto requestAddMemberDto);
    ResponseAddMemberDto removeMemberService(RequestAddMemberDto requestAddMemberDto);
}
