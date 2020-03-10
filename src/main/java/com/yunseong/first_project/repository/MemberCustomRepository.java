package com.yunseong.first_project.repository;

import com.yunseong.first_project.dto.MemberDto;
import com.yunseong.first_project.dto.MemberSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberCustomRepository {

    List<MemberDto> findAllBySearch(MemberSearchCondition condition);

    Page<MemberDto> findPageBySearch(MemberSearchCondition condition, Pageable pageable);
}
