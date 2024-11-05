/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:34 AM - 29/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.asymmetrical.encrypt;

import main.java.server.security.asymmetrical.ASymmetricalKey;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.Base64;

public class RSAEncrypt extends AASymmetricalEncrypt {

    @Override
    public ASymmetricalKey generateKey(int size) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(size);
        var keyPair = generator.generateKeyPair();
        return new ASymmetricalKey(keyPair.getPrivate(), keyPair.getPublic());
    }

    @Override
    public byte[] encrypt(String data) throws Exception {
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String encryptToBase64(byte[] data) throws Exception {
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    @Override
    public String encryptStringToBase64(String data) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(data));
    }
}