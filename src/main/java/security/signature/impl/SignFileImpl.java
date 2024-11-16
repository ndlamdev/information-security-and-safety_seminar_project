/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:18 PM - 16/11/2024
 * User: lam-nguyen
 **/

package main.java.security.signature.impl;

import main.java.security.signature.ISignFile;
import main.java.security.signature.ISignVerifyFile;

import java.io.IOException;
import java.security.*;
import java.util.Base64;

public class SignFileImpl implements ISignFile {
    private static Signature signature;

    private SignFileImpl(String algorithm, PrivateKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        signature = Signature.getInstance(algorithm);
        signature.initSign(key);
    }

    public static ISignFile getInstance(String algorithm, PrivateKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        return new SignFileImpl(algorithm, key);
    }

    @Override
    public String sign(String source) throws IOException, SignatureException {
        ISignVerifyFile.signVerifyHelper(signature, source);
        return Base64.getEncoder().encodeToString(signature.sign());
    }
}
