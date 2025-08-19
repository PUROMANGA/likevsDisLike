package org.example.boardproject.domain;

import org.example.boardproject.api.member.dto.addmemberdto.RequestAddMemberDto;
import org.example.boardproject.api.member.entity.Member;
import org.example.boardproject.api.member.repository.MemberRepository;
import org.example.boardproject.api.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SignUpTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 이메일_전송_테스트() {
        String email = "wlsqkrtk11@naver.com";
        String nickname = "ㅇㅇ";
        RequestAddMemberDto requestAddMemberDto = new RequestAddMemberDto(email, nickname);
        memberService.addMemberService(requestAddMemberDto);
        String foundEmail = memberRepository.findById(1L).map(Member::getEmail).orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));
        assertThat(foundEmail).isEqualTo(email);
    }
}
