package com.rightpair.api.oauth.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUserInfoResponse(
        String id,
        String connectedAt,
        Properties properties,
        KakaoAccount kakaoAccount
) {

    public record Properties(
            String nickname,
            String profileImage,
            String thumbnailImage
    ) {
    }

    public record KakaoAccount(
            boolean profileNeedsAgreement,
            Profile profile,
            boolean hasEmail,
            boolean emailNeedsAgreement,
            boolean isEmailValid,
            boolean isEmailVerified,
            String email,
            boolean hasAgeRange,
            boolean ageRangeNeedsAgreement,
            String ageRange,
            boolean hasBirthday,
            boolean birthdayNeedsAgreement,
            String birthday,
            boolean hasGender,
            boolean genderNeedsAgreement,
            String gender
    ) {
    }

    public record Profile(
            String nickname,
            String thumbnailImageUrl,
            String profileImageUrl,
            boolean isDefaultImage
    ) {
    }
}
