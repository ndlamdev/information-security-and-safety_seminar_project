/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:28 PM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.signature.impl;

import com.lamnguyen.model.signature.ISignVerifyFile;
import com.lamnguyen.model.signature.IVerifySignatureFile;

import java.io.IOException;
import java.security.*;
import java.util.Base64;

public class VerifySignatureFileImpl implements IVerifySignatureFile {
    private static Signature signatureObj;

    private VerifySignatureFileImpl(String algorithm, PublicKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        signatureObj = Signature.getInstance(algorithm);
        signatureObj.initVerify(key);
    }

    public static IVerifySignatureFile getInstance(String algorithm, PublicKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        return new VerifySignatureFileImpl(algorithm, key);
    }

    @Override
    public boolean verify(String source, String signature) throws IOException, SignatureException {
        ISignVerifyFile.signVerifyHelper(signatureObj, source);
        return signatureObj.verify(Base64.getDecoder().decode(signature));
    }
}
