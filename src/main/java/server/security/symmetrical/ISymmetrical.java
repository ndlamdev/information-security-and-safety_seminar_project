/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:49 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.symmetrical;

import main.java.server.security.symmetrical.encrypt.AESEncrypt;
import main.java.server.security.symmetrical.encrypt.DESEncrypt;
import main.java.server.security.symmetrical.encrypt.ISymmetricalEncrypt;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public interface ISymmetrical {
    void loadKey(SecretKey key);

    SecretKey getKey();


    enum SymmetricalAlgorithm {
        AES, DES;
    }

    static SecretKey generateKeyByEncodeKey(String base64Encode, String algorithm) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Encode);
        try {
            return new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /*Encode key thành base64*/
    static String encodeKeyToBase64(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    class Factory {
        public static ISymmetricalEncrypt createEncrypt(ISymmetrical.SymmetricalAlgorithm algorithm, int keySize) {
            return switch (algorithm) {
                case AES -> {
                    var cipher = new AESEncrypt();
                    cipher.loadKey(cipher.generateKey(keySize));
                    yield cipher;
                }
                case DES -> {
                    var cipher = new DESEncrypt();
                    cipher.loadKey(cipher.generateKey(keySize));
                    yield cipher;
                }
            };
        }
    }
}