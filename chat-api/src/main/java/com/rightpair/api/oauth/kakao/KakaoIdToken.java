package com.rightpair.api.oauth.kakao;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class KakaoIdToken extends IdToken {
    public KakaoIdToken(Header header, Payload payload, byte[] signatureBytes, byte[] signedContentBytes) {
        super(header, payload, signatureBytes, signedContentBytes);
    }

    public static KakaoIdToken parse(JsonFactory jsonFactory, String idTokenString) throws IOException {
        JsonWebSignature jws = JsonWebSignature.parser(jsonFactory).setPayloadClass(KakaoIdToken.Payload.class).parse(idTokenString);
        return new KakaoIdToken(jws.getHeader(), (KakaoIdToken.Payload) jws.getPayload(), jws.getSignatureBytes(), jws.getSignedContentBytes());
    }

    public boolean verify(KakaoIdTokenVerifier verifier) throws GeneralSecurityException, IOException {
        return verifier.verify(this);
    }

    public Payload getPayload() {
        return (Payload) super.getPayload();
    }

    @Getter
    @Setter
    public static class Payload extends IdToken.Payload {
        @Key("nickname")
        private String nickname;

        @Key("picture")
        private String picture;

        @Key("email")
        private String email;
    }
}
