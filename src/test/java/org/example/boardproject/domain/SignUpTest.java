package org.example.boardproject.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.boardproject.api.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;

@SpringBootTest
@Slf4j
public class SignUpTest {
    @Autowired
    private MemberService memberService;

    @Test
    void 이메일_전송_테스트() {
        String email = "wlsqkrtk11@naver.com";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
    }
}
