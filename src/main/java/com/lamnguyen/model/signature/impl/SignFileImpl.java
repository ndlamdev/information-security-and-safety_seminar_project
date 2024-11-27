/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:18 PM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.signature.impl;

import com.lamnguyen.model.signature.ISign;
import com.lamnguyen.model.signature.ISignAndVerifyHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class SignFileImpl implements ISign {
    private static Signature signature;

    private SignFileImpl(String algorithm, PrivateKey key) throws NoSuchAlgorithmException, InvalidKeyException {
        signature = Signature.getInstance(algorithm);
        signature.initSign(key);
    }

    private SignFileImpl(String algorithm, String mode, String padding, PrivateKey key) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        String extension = (mode == null || mode.isBlank() ? "" : "/" + mode) + (padding == null || padding.isBlank() ? "" : "/" + padding);
        signature = Signature.getInstance(algorithm + extension, "SunJCE");
        signature.initSign(key);
    }

    public static ISign getInstance(String algorithm, PrivateKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        return new SignFileImpl(algorithm, key);
    }

    public static ISign getInstance(String algorithm, String mode, String padding, PrivateKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        return new SignFileImpl(algorithm, mode, padding, key);
    }

    @Override
    public String signFile(String source) throws IOException, SignatureException {
        ISignAndVerifyHelper.signVerifyHelper(signature, source);
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    @Override
    public String signText(String text) throws SignatureException {
        signature.update(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign());
    }
}
