/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:28 AM - 25/11/2024
 * User: kimin
 **/

package com.lamnguyen.utils;

import com.lamnguyen.model.symmetrical.ChaCha20Poly1305Spec;

import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.UUID;

public class IVUtil {
    public static IvParameterSpec generateIV(int sizeIV) {
        byte[] iv = new byte[sizeIV];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static ChaCha20ParameterSpec generateChaCha20IV(int sizeIV) {
        byte[] iv = new byte[sizeIV];
        new SecureRandom().nextBytes(iv);
        return new ChaCha20ParameterSpec(iv, 0);
    }

    public static ChaCha20Poly1305Spec generateChaCha20Poly1305SpecIV(int sizeIV) {
        return new ChaCha20Poly1305Spec(UUID.randomUUID().toString(), generateIV(sizeIV));
    }
}
