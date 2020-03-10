package com.yunseong.first_project.service;

import com.yunseong.first_project.domain.Member;
import com.yunseong.first_project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member;
        try {
            member = this.memberRepository.findMemberByUsername(username).get();
        } catch (Exception e) {
            throw new UsernameNotFoundException("");
        }
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles("USER")
                .build();
    }
}
