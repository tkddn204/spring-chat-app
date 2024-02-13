package com.rightpair.core.repository.member;

import com.rightpair.core.domain.member.MemberAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberAuthTokenRepository extends JpaRepository<MemberAuthToken, Long> {

    void deleteByMemberId(Long memberId);
    List<MemberAuthToken> findAllByExpiredAtIsAfter(LocalDateTime date);

    Optional<MemberAuthToken> findByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);
}
