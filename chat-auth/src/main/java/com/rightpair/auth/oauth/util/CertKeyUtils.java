package com.rightpair.auth.oauth.util;

import com.rightpair.auth.exception.InvalidOAuthPublicKeyException;
import com.rightpair.auth.service.response.GoogleOAuthCertKeysResponse;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class CertKeyUtils {
    public static PublicKey getPublicKey(GoogleOAuthCertKeysResponse.CertKey certKey) {
        BigInteger modulus = decode(certKey.n());
        BigInteger exponent = decode(certKey.e());
        return generatePublicKey(certKey, modulus, exponent);
    }

    private static BigInteger decode(String keyComponent) {
        return new BigInteger(1, Base64.getUrlDecoder().decode(keyComponent));
    }

    private static PublicKey generatePublicKey(GoogleOAuthCertKeysResponse.CertKey certKey, BigInteger modulus, BigInteger exponent) {
        try {
            return KeyFactory.getInstance(certKey.kty()).generatePublic(
                    new RSAPublicKeySpec(modulus, exponent)
            );
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new InvalidOAuthPublicKeyException();
        }
    }
}
