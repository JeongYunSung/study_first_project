package com.yunseong.first_project.config;

import com.yunseong.first_project.domain.Member;
import com.yunseong.first_project.domain.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@Profile("dev")
public class InitMember {

    @Autowired
    private InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        this.initMemberService.init();
    }

    @Component
    static class InitMemberService {

        @Autowired
        private EntityManager em;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Transactional
        public void init() {
            Team r = new Team("RED");
            Team g = new Team("GREEN");
            Team b = new Team("BLUE");

            this.em.persist(r);this.em.persist(g);this.em.persist(b);

            for(int i=0;i<33;i++) {
                int check = i % 3;
                Member member = check == 0 ?  new Member("Username : " + i, "Nickname : " + i, passwordEncoder.encode("Password : " + i), r) :
                        check == 1 ? new Member("Username : " + i, "Nickname : " + i, passwordEncoder.encode("Password : " + i), g) :
                                new Member("Username : " + i, "Nickname : " + i, passwordEncoder.encode("Password : " + i), b);
                this.em.persist(member);
            }
        }
    }
}
