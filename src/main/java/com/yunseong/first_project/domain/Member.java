package com.yunseong.first_project.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username", "nickname"}))
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
public class Member extends BaseEntity {

    private String username;

    private String nickname;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_name")
    private Team team;

    public Member(String username, String nickname, String password) {
        this(username, nickname, password, null);
    }

    public Member(String username, String nickname, String password, Team team) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.team = team;
    }

    public void changeTeam(Team team) {
        this.team = team;
    }
}
