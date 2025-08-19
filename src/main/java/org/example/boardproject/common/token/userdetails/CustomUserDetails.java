package org.example.boardproject.common.token.userdetails;

import org.example.boardproject.api.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + member.getMemberRole()));
    }

    @Override
    public String getPassword() {
        return member.getCodeNumber();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return member.getIsEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.getIsAccountNonLocked();
    }
}
