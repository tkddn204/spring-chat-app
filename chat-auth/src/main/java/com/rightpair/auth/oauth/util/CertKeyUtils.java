package com.rightpair.auth.oauth.util;

import com.rightpair.auth.dto.GoogleOAuthCertKeysResponse;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class CertKeyUtils {
    public static PublicKey getPublicKey(GoogleOAuthCertKeysResponse.CertKey certKey) {
        BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(certKey.n()));
        BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(certKey.e()));
        try {
            return KeyFactory.getInstance(certKey.kty()).generatePublic(
                    new RSAPublicKeySpec(modulus, exponent)
            );
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
