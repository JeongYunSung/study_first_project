package com.yunseong.first_project.controller.errors;

import com.yunseong.first_project.dto.MemberSearchCondition;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static org.springframework.util.StringUtils.hasText;

@Component
public class MemberSearchValidator {

    public void validate(MemberSearchCondition condition, Errors errors) {
        if(!(hasText(condition.getUsername()) || hasText(condition.getNickname()) || hasText(condition.getTeamname()))) {
            errors.reject("nullPointer", "Username, Nickname, and Teamname are null");
        }
    }
}
