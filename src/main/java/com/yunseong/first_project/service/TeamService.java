package com.yunseong.first_project.service;

import com.yunseong.first_project.domain.Team;
import com.yunseong.first_project.dto.TeamDto;
import com.yunseong.first_project.dto.TeamSearchCondition;
import com.yunseong.first_project.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public Long save(Team team) {
        this.teamRepository.save(team);
        return team.getId();
    }

    public TeamDto findTeamById(Long id) {
        Team team = this.teamRepository.findById(id).get();
        return new TeamDto(team.getTeamname());
    }

    public TeamDto findTeamByTeamname(String teamname) {
        Team team = this.teamRepository.findTeamByTeamname(teamname).get();
        return new TeamDto(team.getTeamname());
    }

    public List<TeamDto> findAll() {
        return this.teamRepository.findAll().stream().map(team -> new TeamDto(team.getTeamname())).collect(Collectors.toList());
    }

    public Page<TeamDto> findAllByPage(Pageable pageable) {
        return this.teamRepository.findAll(pageable).map(team -> new TeamDto(team.getTeamname()));
    }

    public List<TeamDto> findAllBySearch(TeamSearchCondition condition) {
        return this.teamRepository.findAllBySearch(condition);
    }

    public Page<TeamDto> findPageBySearch(TeamSearchCondition condition, Pageable pageable) {
        return this.teamRepository.findPageBySearch(condition, pageable);
    }
}
