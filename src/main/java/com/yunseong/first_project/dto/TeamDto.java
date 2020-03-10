package com.yunseong.first_project.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamDto {

    private String teamname;

    @QueryProjection
    public TeamDto(String teamname) {
        this.teamname = teamname;
    }
}
