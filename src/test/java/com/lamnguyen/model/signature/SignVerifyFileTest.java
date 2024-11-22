/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:41 PM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.signature;

import com.lamnguyen.model.signature.impl.SignFileImpl;
import com.lamnguyen.model.signature.impl.VerifySignatureFileImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.*;

public class SignVerifyFileTest {
    private static ISignFile sign;
    private static IVerifySignatureFile verify;
    private static String file;

    @BeforeAll
    public static void init() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
        pairGenerator.initialize(1024);
        var keyPair = pairGenerator.generateKeyPair();
        sign = SignFileImpl.getInstance("SHA3-384withRSA", keyPair.getPrivate());
        verify = VerifySignatureFileImpl.getInstance("SHA3-384withRSA", keyPair.getPublic());
        file = "/home/lam-nguyen/Desktop/hinh.png";
    }

    @Test
    public void test() throws IOException, SignatureException, NoSuchAlgorithmException, NoSuchProviderException {
        String signature = sign.sign(file);
        System.out.println(signature);
        Assertions.assertTrue(verify.verify(file, signature));
    }
}
