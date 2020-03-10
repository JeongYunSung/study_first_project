package com.yunseong.first_project.repository;

import com.yunseong.first_project.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamCustomRepository {

    Optional<Team> findTeamByTeamname(String teamname);
}
