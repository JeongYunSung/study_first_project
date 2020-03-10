package com.yunseong.first_project.controller;

import com.yunseong.first_project.dto.TeamDto;
import com.yunseong.first_project.dto.TeamSearchCondition;
import com.yunseong.first_project.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.StringUtils.hasText;

@RestController
@RequestMapping(value = "/api/teams", produces = MediaTypes.HAL_JSON_VALUE)
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable Long id) {
        return this.teamService.findTeamById(id);
    }

    @GetMapping
    public Page<TeamDto> getTeams(@PageableDefault Pageable pageable) {
        return this.teamService.findAllByPage(pageable);
    }

    @GetMapping("/search")
    public Page<TeamDto> getTeamsBySearch(TeamSearchCondition condition, @PageableDefault Pageable pageable) {
        if(!hasText(condition.getTeamname())) // 검색결과가 없는 에러페이지 띄워줘야 함
            return this.teamService.findAllByPage(pageable);
        return this.teamService.findPageBySearch(condition, pageable);
    }
}
