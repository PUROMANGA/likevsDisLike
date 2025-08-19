package org.example.boardproject.api.member.common;

import lombok.Getter;

@Getter
public enum MemberRole {
    PARTICIPANT("ROLE_PARTICIPANT"),
    ORGANIZER("ROLE_ORGANIZER"),
    ADMIN("ROLE_ADMIN");

    private final String description;

    MemberRole(String description) {
        this.description = description;
    }
}
