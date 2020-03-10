package com.yunseong.first_project.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yunseong.first_project.dto.MemberDto;
import com.yunseong.first_project.dto.MemberSearchCondition;
import com.yunseong.first_project.dto.QMemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yunseong.first_project.domain.QMember.member;
import static com.yunseong.first_project.domain.QTeam.team;
import static org.springframework.data.repository.support.PageableExecutionUtils.getPage;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class MemberRepositoryImpl implements MemberCustomRepository {

    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<MemberDto> findAllBySearch(MemberSearchCondition condition) {
        List<MemberDto> result = this.queryFactory
                .select(new QMemberDto(member.username, member.nickname, member.team.teamname))
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()), nicknameEq(condition.getNickname()), teamnameEq(condition.getTeamname()))
                .fetch();
        return result;
    }

    public Page<MemberDto> findPageBySearch(MemberSearchCondition condition, Pageable pageable) {
        List<MemberDto> content = this.queryFactory
                .select(new QMemberDto(member.username, member.nickname, member.team.teamname))
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()), nicknameEq(condition.getNickname()), teamnameEq(condition.getTeamname()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return getPage(content, pageable, () -> this.queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()), nicknameEq(condition.getNickname()), teamnameEq(condition.getTeamname()))
                .fetchCount());
    }

    private Predicate usernameEq(String username) {
        return hasText(username) ? member.username.contains(username) : null;
    }

    private Predicate nicknameEq(String nickname) {
        return hasText(nickname) ? member.nickname.contains(nickname) : null;
    }

    private Predicate teamnameEq(String teamname) {
        return hasText(teamname) ? member.team.teamname.contains(teamname) : null;
    }
}
