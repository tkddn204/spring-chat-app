package com.rightpair.auth.oauth.kakao;

import com.google.api.client.auth.openidconnect.IdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Preconditions;
import lombok.Getter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Iterator;

public class KakaoIdTokenVerifier extends IdTokenVerifier {
    private final KakaoPublicKeysManager publicKeysManager;

    public KakaoIdTokenVerifier(HttpTransport transport, JsonFactory jsonFactory) {
        this(new KakaoIdTokenVerifier.Builder(transport, jsonFactory));
    }

    public KakaoIdTokenVerifier(KakaoPublicKeysManager publicKeys) {
        this(new Builder(publicKeys));
    }

    protected KakaoIdTokenVerifier(KakaoIdTokenVerifier.Builder builder) {
        super(builder);
        this.publicKeysManager = builder.publicKeysManager;
    }

    public final JsonFactory getJsonFactory() {
        return this.publicKeysManager.getJsonFactory();
    }

    public boolean verify(KakaoIdToken KakaoIdToken) throws GeneralSecurityException, IOException {
        if (!super.verifyPayload(KakaoIdToken)) {
            return false;
        } else {
            Iterator<PublicKey> publicKeyIterator = this.publicKeysManager.getPublicKeys().iterator();

            PublicKey publicKey;
            do {
                if (!publicKeyIterator.hasNext()) {
                    return false;
                }

                publicKey = publicKeyIterator.next();
            } while (!KakaoIdToken.verifySignature(publicKey));

            return true;
        }
    }

    public KakaoIdToken verify(String idTokenString) throws GeneralSecurityException, IOException {
        KakaoIdToken idToken = KakaoIdToken.parse(this.getJsonFactory(), idTokenString);
        return this.verify(idToken) ? idToken : null;
    }

    @Getter
    public static class Builder extends IdTokenVerifier.Builder {
        private final KakaoPublicKeysManager publicKeysManager;

        public Builder(HttpTransport transport, JsonFactory jsonFactory) {
            this(new KakaoPublicKeysManager.Builder(transport, jsonFactory).build());
        }

        public Builder(KakaoPublicKeysManager publicKeys) {
            this.publicKeysManager = Preconditions.checkNotNull(publicKeys);
            this.setIssuers(Arrays.asList("kauth.kakao.com", "https://kauth.kakao.com"));
        }

        public KakaoIdTokenVerifier build() {
            return new KakaoIdTokenVerifier(this);
        }
    }
}
