/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:34 AM - 29/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.asymmetrical.encrypt;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class RSAEncrypt extends AASymmetricalEncrypt {
    public RSAEncrypt() {
    }

    public RSAEncrypt(String mode, String padding) {
        this.mode = mode;
        this.padding = padding;
    }

    @Override
    protected KeyPairGenerator initKeyPairGenerator() throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance(Algorithms.RSA.name());
    }

    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        cipher = Cipher.getInstance(Algorithms.RSA.name() + getExtension(), "SunJCE");
    }
}