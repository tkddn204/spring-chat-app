package com.rightpair.auth.oauth.kakao;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.StringUtils;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KakaoPublicKeysManager {
    //    private static final long REFRESH_SKEW_MILLIS = 300000L;
    private static final Pattern MAX_AGE_PATTERN = Pattern.compile("\\s*max-age\\s*=\\s*(\\d+)\\s*");
    private final JsonFactory jsonFactory;
    private final HttpTransport transport;
    private final Lock lock;
    private final Clock clock;
    private final String publicCertsEncodedUrl;
    private List<PublicKey> publicKeys;
    private long expirationTimeMilliseconds;

    public KakaoPublicKeysManager(HttpTransport transport, JsonFactory jsonFactory) {
        this(new KakaoPublicKeysManager.Builder(transport, jsonFactory));
    }

    protected KakaoPublicKeysManager(KakaoPublicKeysManager.Builder builder) {
        this.lock = new ReentrantLock();
        this.transport = builder.transport;
        this.jsonFactory = builder.jsonFactory;
        this.clock = builder.clock;
        this.publicCertsEncodedUrl = builder.publicCertsEncodedUrl;
    }

    public final HttpTransport getTransport() {
        return this.transport;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final List<PublicKey> getPublicKeys() throws GeneralSecurityException, IOException {
        this.lock.lock();

        List<PublicKey> keys;
        try {
            if (this.publicKeys == null || this.clock.currentTimeMillis() + 300000L > this.expirationTimeMilliseconds) {
                this.refresh();
            }

            keys = this.publicKeys;
        } finally {
            this.lock.unlock();
        }

        return keys;
    }

    public KakaoPublicKeysManager refresh() throws GeneralSecurityException, IOException {
        this.lock.lock();

        KakaoPublicKeysManager kakaoPublicKeysManager;
        try {
            this.publicKeys = new ArrayList<>();
            CertificateFactory factory = SecurityUtils.getX509CertificateFactory();
            HttpResponse certsResponse = this.transport.createRequestFactory().buildGetRequest(new GenericUrl(this.publicCertsEncodedUrl)).execute();
            this.expirationTimeMilliseconds = this.clock.currentTimeMillis() + this.getCacheTimeInSec(certsResponse.getHeaders()) * 1000L;
            JsonParser parser = this.jsonFactory.createJsonParser(certsResponse.getContent());
            JsonToken currentToken = parser.getCurrentToken();
            if (currentToken == null) {
                currentToken = parser.nextToken();
            }

            Preconditions.checkArgument(currentToken == JsonToken.START_OBJECT);

            try {
                while (true) {
                    if (parser.nextToken() == JsonToken.END_OBJECT) {
                        this.publicKeys = Collections.unmodifiableList(this.publicKeys);
                        break;
                    }

                    parser.nextToken();
                    String certValue = parser.getText();
                    X509Certificate x509Cert = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(StringUtils.getBytesUtf8(certValue)));
                    this.publicKeys.add(x509Cert.getPublicKey());
                }
            } finally {
                parser.close();
            }

            kakaoPublicKeysManager = this;
        } finally {
            this.lock.unlock();
        }

        return kakaoPublicKeysManager;
    }

    long getCacheTimeInSec(HttpHeaders httpHeaders) {
        long cacheTimeInSec = 0L;
        if (httpHeaders.getCacheControl() != null) {
            String[] cacheControls = httpHeaders.getCacheControl().split(",");
            for (String cacheControlStr : cacheControls) {
                Matcher m = MAX_AGE_PATTERN.matcher(cacheControlStr);
                if (m.matches()) {
                    cacheTimeInSec = Long.parseLong(m.group(1));
                    break;
                }
            }
        }

        if (httpHeaders.getAge() != null) {
            cacheTimeInSec -= httpHeaders.getAge();
        }

        return Math.max(0L, cacheTimeInSec);
    }

    @Getter
    public static class Builder {
        final HttpTransport transport;
        final JsonFactory jsonFactory;
        Clock clock;
        String publicCertsEncodedUrl;

        public Builder(HttpTransport transport, JsonFactory jsonFactory) {
            this.clock = Clock.SYSTEM;
            this.publicCertsEncodedUrl = "https://kauth.kakao.com/.well-known/jwks.json";
            this.transport = Preconditions.checkNotNull(transport);
            this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        }

        public KakaoPublicKeysManager build() {
            return new KakaoPublicKeysManager(this);
        }

    }
}
