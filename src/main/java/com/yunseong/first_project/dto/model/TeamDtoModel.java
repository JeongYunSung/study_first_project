package com.yunseong.first_project.dto.model;

import com.yunseong.first_project.dto.TeamDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class TeamDtoModel extends EntityModel<TeamDto> {

    public TeamDtoModel(TeamDto content, Link... links) {
        super(content, links);
    }
}
