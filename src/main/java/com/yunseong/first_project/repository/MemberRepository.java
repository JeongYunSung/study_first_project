package com.yunseong.first_project.repository;

import com.yunseong.first_project.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    @Query("select m, t from Member m join fetch m.team t where m.id = :id")
    Optional<Member> findMemberById(Long id);

    @Query("select m, t from Member m join fetch m.team t where m.username = :username")
    Optional<Member> findMemberByUsername(String username);

    @Query("select m, t from Member m join fetch m.team t where m.nickname = :nickname")
    Optional<Member> findMemberByNickname(String nickname);

    @Query(value = "select m, t from Member m join fetch m.team t", countQuery = "select count(m) from Member m")
    Page<Member> findFetchAll(Pageable pageable);
}
