package com.yunseong.first_project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yunseong.first_project.dto.QTeamDto;
import com.yunseong.first_project.dto.TeamDto;
import com.yunseong.first_project.dto.TeamSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yunseong.first_project.domain.QTeam.team;
import static org.springframework.data.repository.support.PageableExecutionUtils.getPage;

@Repository
public class TeamRepositoryImpl implements TeamCustomRepository {

    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public TeamRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TeamDto> findAllBySearch(TeamSearchCondition condition) {
        return this.queryFactory
                .select(new QTeamDto(team.teamname))
                .from(team)
                .where(team.teamname.contains(condition.getTeamname()))
                .fetch();
    }

    public Page<TeamDto> findPageBySearch(TeamSearchCondition condition, Pageable pageable) {
        List<TeamDto> content = this.queryFactory
                .select(new QTeamDto(team.teamname))
                .from(team)
                .where(team.teamname.eq(condition.getTeamname()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return getPage(content, pageable, () -> this.queryFactory
                .selectFrom(team)
                .where(team.teamname.contains(condition.getTeamname()))
                .fetchCount());
    }
}
