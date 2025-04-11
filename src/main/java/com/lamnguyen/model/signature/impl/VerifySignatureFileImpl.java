/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:28 PM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.signature.impl;

import com.lamnguyen.model.signature.ISignAndVerifyHelper;
import com.lamnguyen.model.signature.IVerifySignature;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class VerifySignatureFileImpl implements IVerifySignature {
    private static Signature signatureObj;

    private VerifySignatureFileImpl(String algorithm, PublicKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        signatureObj = Signature.getInstance(algorithm);
        signatureObj.initVerify(key);
    }

    private VerifySignatureFileImpl(String algorithm, String mode, String padding, PublicKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        String extension = (mode == null || mode.isBlank() ? "" : "/" + mode) + (padding == null || padding.isBlank() ? "" : "/" + padding);
        signatureObj = Signature.getInstance(algorithm + extension, "SunJCE");
        signatureObj.initVerify(key);
    }

    public static IVerifySignature getInstance(String algorithm, PublicKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        return new VerifySignatureFileImpl(algorithm, key);
    }

    public static IVerifySignature getInstance(String algorithm, String mode, String padding, PublicKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        return new VerifySignatureFileImpl(algorithm, mode, padding, key);
    }

    @Override
    public boolean verifyFile(String source, String signature) throws IOException, SignatureException {
        ISignAndVerifyHelper.signVerifyHelper(signatureObj, source);
        return signatureObj.verify(Base64.getDecoder().decode(signature));
    }

    @Override
    public boolean verifyText(String text, String signature) throws SignatureException {
        signatureObj.update(text.getBytes(StandardCharsets.UTF_8));
        return signatureObj.verify(Base64.getDecoder().decode(signature));
    }
}
