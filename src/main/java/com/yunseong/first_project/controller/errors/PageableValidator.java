package com.yunseong.first_project.controller.errors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PageableValidator {

    public <T> void validate(Page<T> page, Pageable pageable, Errors errors) {
        if(pageable.getPageNumber() < 0 || (page.getTotalPages()-1) < pageable.getPageNumber()) {
            errors.rejectValue("pageNumber", "wrongValue", "pageNumber is wrongValue");
        }
    }
}
