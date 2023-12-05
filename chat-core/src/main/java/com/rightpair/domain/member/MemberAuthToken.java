package com.rightpair.domain.member;

import com.rightpair.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAuthToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    private MemberAuthToken(Member member, String refreshToken, LocalDateTime expiredAt) {
        this.member = member;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }

    public static MemberAuthToken create(Member member, String refreshToken, LocalDateTime expiredAt) {
        return new MemberAuthToken(member, refreshToken, expiredAt);
    }
}
