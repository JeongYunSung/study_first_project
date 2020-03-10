package com.yunseong.first_project.dto;

import lombok.Data;

@Data
public class TeamSearchCondition {

    private String teamname;

    public TeamSearchCondition(String teamname) {
        this.teamname = teamname;
    }
}
