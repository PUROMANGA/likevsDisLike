package org.example.boardproject.api.member.dto.addmemberdto;

import lombok.Getter;

@Getter
public class RequestAddMemberDto {
    private String email;
    private String nickName;

    public RequestAddMemberDto(String email, String nickName) {
        this.email = email;
        this.nickName = nickName;
    }
}
