package com.yunseong.first_project.service;

import com.yunseong.first_project.domain.Member;
import com.yunseong.first_project.dto.MemberDto;
import com.yunseong.first_project.dto.MemberSearchCondition;
import com.yunseong.first_project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public Long save(Member member) {
        this.memberRepository.save(member);
        return member.getId();
    }

    public MemberDto findMemberById(Long id) {
        return new MemberDto(this.memberRepository.findMemberById(id).get());
    }

    public MemberDto findMemberByUsername(String username) {
        return new MemberDto(this.memberRepository.findMemberByUsername(username).get());
    }

    public MemberDto findMemberByNickname(String nickname) {
        return new MemberDto(this.memberRepository.findMemberByNickname(nickname).get());
    }

    public List<MemberDto> findAllBySearch(MemberSearchCondition condition) {
        return this.memberRepository.findAllBySearch(condition);
    }

    public Page<MemberDto> findPageBySearch(MemberSearchCondition condition, Pageable pageable) {
        return this.memberRepository.findPageBySearch(condition, pageable);
    }

    public List<MemberDto> findAll() {
        return this.memberRepository.findAll().stream().map(MemberDto::new).collect(Collectors.toList());
    }

    public List<Member> findAll2() {
        return this.memberRepository.findAll();
    }

    public Page<MemberDto> findAllByPage(Pageable pageable) {
        return this.memberRepository.findFetchAll(pageable).map(MemberDto::new);
    }
}
