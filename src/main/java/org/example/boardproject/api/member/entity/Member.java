package org.example.boardproject.api.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.boardproject.api.member.common.MemberRole;
import org.example.boardproject.api.member.dto.addmemberdto.RequestAddMemberDto;
import org.example.boardproject.common.base.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "members")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String nickName;
    private String codeNumber;
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
    private Boolean isAccountNonLocked;
    private Boolean isEnabled;

    public Member(RequestAddMemberDto requestAddMemberDto, String codeNumber, MemberRole memberRole) {
        this.email = requestAddMemberDto.getEmail();
        this.nickName = requestAddMemberDto.getNickName();
        this.codeNumber = codeNumber;
        this.memberRole = memberRole;
        this.isAccountNonLocked = false;
        this.isEnabled = false;
    }
}
