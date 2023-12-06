package com.rightpair.repository;

import com.rightpair.domain.member.MemberOpenAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberOpenAuthRepository extends JpaRepository<MemberOpenAuth, Long> {
    @Query("select m from MemberOpenAuth m where m.member.email = ?1")
    Optional<MemberOpenAuth> findByMemberEmail(String email);
}
