package com.yunseong.first_project.dto.model;

import com.yunseong.first_project.dto.MemberDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class MemberDtoModel extends EntityModel<MemberDto> {

    public MemberDtoModel(MemberDto content, Link... links) {
        super(content, links);
    }
}
