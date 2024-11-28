/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:17 AM - 11/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.asymmetrical;

import com.lamnguyen.model.asymmetrical.decrypt.IASymmetricalDecrypt;
import com.lamnguyen.model.asymmetrical.decrypt.RSADecrypt;
import com.lamnguyen.model.asymmetrical.encrypt.IASymmetricalEncrypt;
import com.lamnguyen.model.asymmetrical.encrypt.RSAEncrypt;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public interface IAsymmetrical {
    /**
     * Encode khóa công khai thành base64
     *
     * @param publicKey PublicKey khóa công khai cần được encode
     * @return String dẫy base64 của khóa công khai
     */
    static String encodeKeyToBase64(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * Encode khóa riêng tư thành base64
     *
     * @param privateKey PrivateKey khóa riêng tư cần được encode
     * @return String dẫy base64 của khóa riêng tư
     */
    static String encodeKeyToBase64(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * Enum các thuật toán mà phần mềm hổ trợ
     */
    enum Algorithms {
        DSA,
        RSA,
        ECIES
    }


    /**
     * Đối tượng dùng để hổ trợ việc tạo các Encrypt và Decrypt
     */
    class Factory {

        /**
         * Tạo ra đối tượng encrypt từ enum thuật toán.
         *
         * @param algorithm Algorithms enum thuật toán muốn tạo
         * @return IASymmetricalEncrypt đối tượng encrypt
         */
        public static IASymmetricalEncrypt createEncrypt(Algorithms algorithm) {
            return switch (algorithm) {
                case RSA -> new RSAEncrypt();
                default -> null;
            };
        }

        /**
         * Tạo ra đối tượng encrypt từ enum thuật toán, chuổi mode và chuổi padding.
         *
         * @param algorithm Algorithms enum thuật toán muốn tạo
         * @param mode      String mode muốn dùng
         * @param padding   String padding muốn dùng
         * @return IASymmetricalEncrypt đối tượng encrypt
         */
        public static IASymmetricalEncrypt createEncrypt(Algorithms algorithm, String mode, String padding) {
            return switch (algorithm) {
                case RSA -> new RSAEncrypt(mode, padding);
                default -> null;
            };
        }

        /**
         * Tạo ra đối tượng encrypt từ chuổi thuật toán, chuổi mode và chuổi padding.
         *
         * @param algorithm Algorithms chuổi thuật toán muốn tạo
         * @param mode      String mode muốn dùng
         * @param padding   String padding muốn dùng
         * @return IASymmetricalEncrypt đối tượng encrypt
         */
        public static IASymmetricalEncrypt createEncrypt(String algorithm, String mode, String padding) {
            return createEncrypt(Algorithms.valueOf(algorithm), mode, padding);
        }

        /**
         * Tạo ra đối tượng encrypt từ chuổi thuật toán.
         *
         * @param algorithm Algorithms chuổi thuật toán muốn tạo
         * @return IASymmetricalEncrypt đối tượng encrypt
         */
        public static IASymmetricalEncrypt createEncrypt(String algorithm) {
            return createEncrypt(Algorithms.valueOf(algorithm));
        }

        /**
         * Tạo ra đối tượng decrypt từ enum thuật toán.
         *
         * @param algorithm Algorithms enum thuật toán muốn tạo
         * @return IASymmetricalDecrypt đối tượng decrypt
         */
        public static IASymmetricalDecrypt createDecrypt(Algorithms algorithm) {
            return switch (algorithm) {
                case RSA -> new RSADecrypt();
                default -> null;
            };
        }

        /**
         * Tạo ra đối tượng decrypt từ enum thuật toán, chuổi mode và chuổi padding.
         *
         * @param algorithm Algorithms enum thuật toán muốn tạo
         * @param mode      String mode muốn dùng
         * @param padding   String padding muốn dùng
         * @return IASymmetricalEncrypt đối tượng decrypt
         */
        public static IASymmetricalDecrypt createDecrypt(Algorithms algorithm, String mode, String padding) {
            return switch (algorithm) {
                case RSA -> new RSADecrypt(mode, padding);
                default -> null;
            };
        }

        /**
         * Tạo ra đối tượng decrypt từ chuổi thuật toán.
         *
         * @param algorithm Algorithms chuổi thuật toán muốn tạo
         * @return IASymmetricalDecrypt đối tượng decrypt
         */
        public static IASymmetricalDecrypt createDecrypt(String algorithm) {
            return createDecrypt(Algorithms.valueOf(algorithm));
        }

        /**
         * Tạo ra đối tượng decrypt từ chuổi thuật toán, chuổi mode và chuổi padding.
         *
         * @param algorithm Algorithms chuổi thuật toán muốn tạo
         * @param mode      String mode muốn dùng
         * @param padding   String padding muốn dùng
         * @return IASymmetricalEncrypt đối tượng decrypt
         */
        public static IASymmetricalDecrypt createDecrypt(String algorithm, String mode, String padding) {
            return createDecrypt(Algorithms.valueOf(algorithm), mode, padding);
        }
    }


    /**
     * Đối tượng hổ trợ việc tạo ra các key của thuật toán bất đối xứng
     */
    class KeyFactory {
        /**
         * Enum thuật toán tạo khóa mà phần mềm hổ trợ
         */
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

        /**
         * Tạo ra một khóa bất đối xứng
         *
         * @param alg     Algorithms enum thuât toán khóa muốn tạo
         * @param sizeKey int kích thước khóa muốn tạo
         * @return AsymmetricalKey khóa bất đối xứng được tạo
         */
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

        /**
         * Đọc khóa bất đối xứng từ file nguồn
         * <ul>
         *    <li>khóa công khai</li>
         *    <li>khóa riêng tư</li>
         * </ul>
         *
         * @param file String đuờng dẫn file khóa
         * @return AsymmetricalKey khóa bất đối xứng đọc được
         */
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

        /**
         * Đọc khóa công khai từ file nguồn
         *
         * @param file String đuờng dẫn file khóa
         * @return PublicKey khóa công khai đọc được
         */
        public static PublicKey readPublicKey(String file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
            DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file), 1024 * 10));
            String algorithm = input.readUTF();
            String base64PublicKey = input.readUTF();
            input.close();
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey));
            java.security.KeyFactory kf = java.security.KeyFactory.getInstance(algorithm);
            return kf.generatePublic(x509EncodedKeySpec);
        }

        /**
         * Đọc khóa riêng tư từ file nguồn
         *
         * @param file String đuờng dẫn file khóa
         * @return PublicKey khóa riêng tư đọc được
         */
        public static PrivateKey readPrivateKey(String file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
            DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file), 1024 * 10));
            String algorithm = input.readUTF();
            String base64PrivateKey = input.readUTF();
            input.close();
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey));
            java.security.KeyFactory kf = java.security.KeyFactory.getInstance(algorithm);
            return kf.generatePrivate(pkcs8EncodedKeySpec);
        }
    }

    /**
     * Lưu khóa bất đối xứng vào file đích
     * <ul>
     *    <li>khóa công khai</li>
     *    <li>khóa riêng tư</li>
     * </ul>
     *
     * @param algorithm Algorithms enum thuật toán của khóa
     * @param key       AsymmetricalKey khóa bất đối xừng
     * @param file      String đường dẫn đến file lưu khóa
     */
    static void saveKey(IAsymmetrical.KeyFactory.Algorithms algorithm, AsymmetricalKey key, String file) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file), 1024 * 10));
        outputStream.writeUTF(algorithm.name());
        outputStream.writeUTF(encodeKeyToBase64(key.privateKey()));
        outputStream.writeUTF(encodeKeyToBase64(key.publicKey()));
        outputStream.close();
    }

    /**
     * Lưu khóa bất đối xứng vào file đích
     * <ul>
     *    <li>khóa công khai</li>
     *    <li>khóa riêng tư</li>
     * </ul>
     *
     * @param algorithm Algorithms chuổi thuật toán của khóa
     * @param key       AsymmetricalKey khóa bất đối xừng
     * @param file      String đường dẫn đến file lưu khóa
     */
    static void saveKey(String algorithm, AsymmetricalKey key, String file) throws IOException {
        saveKey(KeyFactory.Algorithms.valueOf(algorithm), key, file);
    }

    /**
     * Lưu khóa công khai vào file đích
     *
     * @param algorithm Algorithms enum thuật toán của khóa
     * @param key       PublicKey khóa công khai cần lưu
     * @param file      String đường dẫn đến file lưu khóa
     */
    static void savePublicKey(IAsymmetrical.KeyFactory.Algorithms algorithm, PublicKey key, String file) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file), 1024 * 10));
        outputStream.writeUTF(algorithm.name());
        outputStream.writeUTF(encodeKeyToBase64(key));
        outputStream.close();
    }

    /**
     * Lưu khóa công khai vào file đích
     *
     * @param algorithm Algorithms chuổi thuật toán của khóa
     * @param key       PublicKey khóa công khai cần lưu
     * @param file      String đường dẫn đến file lưu khóa
     */
    static void savePublicKey(String algorithm, PublicKey key, String file) throws IOException {
        savePublicKey(KeyFactory.Algorithms.valueOf(algorithm), key, file);
    }

    /**
     * Lưu khóa khóa riêng vào file đích
     *
     * @param algorithm Algorithms enum thuật toán của khóa
     * @param key       PrivateKey khóa riêng tư cần lưu
     * @param file      String đường dẫn đến file lưu khóa
     */
    static void savePrivateKey(IAsymmetrical.KeyFactory.Algorithms algorithm, PrivateKey key, String file) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file), 1024 * 10));
        outputStream.writeUTF(algorithm.name());
        outputStream.writeUTF(encodeKeyToBase64(key));
        outputStream.close();
    }

    /**
     * Lưu khóa khóa riêng vào file đích
     *
     * @param algorithm Algorithms chuổi thuật toán của khóa
     * @param key       PrivateKey khóa riêng tư cần lưu
     * @param file      String đường dẫn đến file lưu khóa
     */
    static void savePrivateKey(String algorithm, PrivateKey key, String file) throws IOException {
        savePrivateKey(KeyFactory.Algorithms.valueOf(algorithm), key, file);
    }
}
