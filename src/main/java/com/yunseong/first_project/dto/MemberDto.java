package com.yunseong.first_project.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yunseong.first_project.domain.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {

    private String username;
    private String nickname;
    private String teamname;

    @QueryProjection
    public MemberDto(String username, String nickname, String teamname) {
        this.username = username;
        this.nickname = nickname;
        this.teamname = teamname;
    }

    public MemberDto(Member member) {
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.teamname = member.getTeam().getTeamname();
    }
}
