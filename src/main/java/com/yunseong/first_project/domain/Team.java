package com.yunseong.first_project.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "teamname"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "team_id"))
public class Team extends BaseUserEntity {

    private String teamname;

    public Team(String teamname) {
        this.teamname = teamname;
    }

/*    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();*/
}
