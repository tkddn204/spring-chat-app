package com.rightpair.domain.member;

import com.rightpair.type.OauthProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberOpenAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OauthProvider provider;

    @Column(nullable = false)
    private String providerId;

    private MemberOpenAuth(Member member, OauthProvider provider, String providerId) {
        this.member = member;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static MemberOpenAuth create(Member member, OauthProvider oauthProvider, String providerId) {
        return new MemberOpenAuth(member, oauthProvider, providerId);
    }

    public void updateProviderId(String providerId) {
        this.providerId = providerId;
    }
}
