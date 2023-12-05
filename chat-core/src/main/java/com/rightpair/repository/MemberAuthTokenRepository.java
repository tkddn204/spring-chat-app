package com.rightpair.repository;

import com.rightpair.domain.member.MemberAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberAuthTokenRepository extends JpaRepository<MemberAuthToken, Long> {
    List<MemberAuthToken> findAllByExpiredAtIsAfter(LocalDateTime date);
}
