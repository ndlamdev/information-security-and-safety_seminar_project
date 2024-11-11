/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:49 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.security.symmetrical;

import main.java.security.symmetrical.decrypt.AESDecrypt;
import main.java.security.symmetrical.decrypt.DESDecrypt;
import main.java.security.symmetrical.decrypt.ISymmetricalDecrypt;
import main.java.security.symmetrical.encrypt.AESEncrypt;
import main.java.security.symmetrical.encrypt.DESEncrypt;
import main.java.security.symmetrical.encrypt.ISymmetricalEncrypt;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public interface ISymmetrical {

    /**
     * Tải khóa bí mật vào cipher.
     *
     * @param key Khóa bí mật cần tải
     * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán mã hóa
     * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại
     * @throws InvalidKeyException      Lỗi key không phù hợp với thuật toán
     */
    void loadKey(SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException;

    /**
     * Lấy khóa bí mật hiện tại.
     *
     * @return Khóa bí mật hiện tại
     */
    SecretKey getKey();

    /**
     * Tạo SecretKey từ chuỗi Base64 đã mã hóa.
     *
     * @param base64Encode Chuỗi Base64 đại diện cho key đã mã hóa
     * @param algorithm    Thuật toán mã hóa (ví dụ: AES, DES)
     * @return SecretKey được tạo từ chuỗi Base64 đã mã hóa
     */
    static SecretKey generateKeyByEncodeKey(String base64Encode, String algorithm) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Encode);
        try {
            return new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Mã hóa SecretKey đã cho thành chuỗi Base64.
     *
     * @param key SecretKey cần được mã hóa
     * @return chuỗi Base64 đại diện cho key
     */
    static String encodeKeyToBase64(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    class Factory {
        /**
         * Hổ trợ tạo một interface mã hóa đối xứng.
         *
         * @param algorithm Thuật toán mã hóa đối xứng muốn tạo.
         * @param keySize   Chiều dài của khóa tương ứng với từng thuật toán.
         * @return ISymmetricalEncrypt: Một interface mã hóa đối xứng.
         * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán  mã hóa.
         * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại.
         * @throws InvalidKeyException      Lỗi key không phù hợp với thuật toán.
         */
        public static ISymmetricalEncrypt createEncrypt(Algorithms algorithm, int keySize) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
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

        /**
         * Hổ trợ tạo một interface giải mã đối xứng.
         *
         * @param algorithm Thuật toán giải mã đối xứng muốn tạo.
         * @param key       Key để tạo cipher giải mã.
         * @return ISymmetricalDecrypt Một interface giải mã đối xứng.
         * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán  mã hóa.
         * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại.
         * @throws InvalidKeyException      Lỗi key không phù hợp với thuật toán.
         */
        public static ISymmetricalDecrypt createDecrypt(Algorithms algorithm, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
            return switch (algorithm) {
                case AES -> new AESDecrypt(key);
                case DES -> new DESDecrypt(key);
            };
        }
    }

    enum Algorithms {
        AES, DES;
    }
}