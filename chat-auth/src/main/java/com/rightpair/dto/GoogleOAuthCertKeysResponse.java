package com.rightpair.dto;

import java.util.List;

public record GoogleOAuthCertKeysResponse(
        List<CertKey> keys
) {
    public record CertKey(
            String kty,
            String alg,
            String kid,
            String n,
            String e,
            String use
    ) {
    }
}
