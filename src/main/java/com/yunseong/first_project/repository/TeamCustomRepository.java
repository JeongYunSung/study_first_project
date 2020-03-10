package com.yunseong.first_project.repository;

import com.yunseong.first_project.dto.TeamDto;
import com.yunseong.first_project.dto.TeamSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamCustomRepository {

    List<TeamDto> findAllBySearch(TeamSearchCondition condition);

    Page<TeamDto> findPageBySearch(TeamSearchCondition condition, Pageable pageable);
}
