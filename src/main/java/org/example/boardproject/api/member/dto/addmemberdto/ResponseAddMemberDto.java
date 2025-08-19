package org.example.boardproject.api.member.dto.addmemberdto;

import lombok.Getter;
import org.example.boardproject.api.member.entity.Member;

@Getter
public class ResponseAddMemberDto {
    private final String email;
    private final String nickName;

    public ResponseAddMemberDto(Member member) {
        this.email = member.getEmail();
        this.nickName = member.getNickName();
    }
}
