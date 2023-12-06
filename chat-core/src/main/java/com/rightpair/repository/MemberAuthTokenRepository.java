package com.rightpair.repository;

import com.rightpair.domain.member.MemberAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MemberAuthTokenRepository extends JpaRepository<MemberAuthToken, Long> {

    void deleteByMemberId(Long memberId);
    List<MemberAuthToken> findAllByExpiredAtIsAfter(LocalDateTime date);
}
