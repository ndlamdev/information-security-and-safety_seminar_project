/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:17 AM - 11/11/2024
 * User: lam-nguyen
 **/

package main.java.security.asymmetrical;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public interface IAsymmetrical {
    static String encodeKeyToBase64(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    static String encodeKeyToBase64(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    enum Algorithms {
        DSA,
        RSA,
        EC,
    }

    class KeyFactory {
        public enum Algorithms {
            DiffieHellman,
            DSA,
            EC,
            EdDSA,
            Ed25519,
            Ed448,
            RSA,
            RSASSA_PSS,
            XDH,
            X25519,
            X448
        }

        public static ASymmetricalKey generateKey(Algorithms alg, int sizeKey) {
            KeyPairGenerator generator = null;
            try {
                generator = KeyPairGenerator.getInstance(alg.name());
                generator.initialize(sizeKey);
            } catch (NoSuchAlgorithmException e) {
                return null;
            }

            var keyPair = generator.generateKeyPair();
            return new ASymmetricalKey(keyPair.getPrivate(), keyPair.getPublic());
        }
    }
}
