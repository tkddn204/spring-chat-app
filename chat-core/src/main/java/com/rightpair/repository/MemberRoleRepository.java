package com.rightpair.repository;

import com.rightpair.domain.member.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
    List<MemberRole> findAllByMemberId(long memberId);
}
