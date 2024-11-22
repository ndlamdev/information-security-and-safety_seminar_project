/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:17 AM - 11/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.asymmetrical;

import java.io.*;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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
        ECIES
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

        public static AsymmetricalKey generateKey(Algorithms alg, int sizeKey) {
            KeyPairGenerator generator = null;
            try {
                generator = KeyPairGenerator.getInstance(alg.name());
                generator.initialize(sizeKey);
            } catch (NoSuchAlgorithmException e) {
                return null;
            }

            var keyPair = generator.generateKeyPair();
            return new AsymmetricalKey(keyPair.getPrivate(), keyPair.getPublic());
        }

        public static AsymmetricalKey readKey(String file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
            DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file), 1024 * 10));
            String algorithm = input.readUTF();
            String base64PrivateKey = input.readUTF();
            String base64PublicKey = input.readUTF();
            input.close();
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey));
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey));
            java.security.KeyFactory kf = java.security.KeyFactory.getInstance(algorithm);
            PrivateKey privateKey = kf.generatePrivate(pkcs8EncodedKeySpec);
            PublicKey publicKey = kf.generatePublic(x509EncodedKeySpec);
            return new AsymmetricalKey(privateKey, publicKey);
        }
    }

    static void saveKey(IAsymmetrical.KeyFactory.Algorithms algorithm, AsymmetricalKey key, String file) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file), 1024 * 10));
        outputStream.writeUTF(algorithm.name());
        outputStream.writeUTF(encodeKeyToBase64(key.privateKey()));
        outputStream.writeUTF(encodeKeyToBase64(key.publicKey()));
        outputStream.close();
    }

    static void saveKey(String algorithm, AsymmetricalKey key, String file) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file), 1024 * 10));
        outputStream.writeUTF(algorithm);
        outputStream.writeUTF(encodeKeyToBase64(key.privateKey()));
        outputStream.writeUTF(encodeKeyToBase64(key.publicKey()));
        outputStream.close();
    }
}
