package com.yunseong.first_project.repository;

import com.yunseong.first_project.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void createTest() throws Exception {
        //given
        Member m = new Member("test", "test", "test");
        //when
        this.memberRepository.save(m);
        //then
        System.out.println(m.getCreatedDate());
        System.out.println(m.getLastModifiedDate());
    }
}