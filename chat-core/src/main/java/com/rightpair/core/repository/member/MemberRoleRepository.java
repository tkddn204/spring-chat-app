package com.rightpair.core.repository.member;

import com.rightpair.core.domain.member.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
    List<MemberRole> findAllByMemberId(long memberId);
}
