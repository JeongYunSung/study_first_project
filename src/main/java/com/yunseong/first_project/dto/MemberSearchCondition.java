package com.yunseong.first_project.dto;

import lombok.Data;

@Data
public class MemberSearchCondition {

    private String username;
    private String nickname;
    private String teamname;

    public MemberSearchCondition(String username, String nickname, String teamname) {
        this.username = username;
        this.nickname = nickname;
        this.teamname = teamname;
    }
}
