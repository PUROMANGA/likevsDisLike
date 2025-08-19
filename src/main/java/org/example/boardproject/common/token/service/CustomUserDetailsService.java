package org.example.boardproject.common.token.service;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.member.entity.Member;
import org.example.boardproject.api.member.repository.MemberRepository;
import org.example.boardproject.common.token.userdetails.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        return new CustomUserDetails(member);
    }
}
