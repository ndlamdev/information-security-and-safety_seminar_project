/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:49 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical;

import com.lamnguyen.model.symmetrical.decrypt.*;
import com.lamnguyen.model.symmetrical.encrypt.*;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.AlgorithmParameterSpec;
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
    void loadKey(SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException;

    /**
     * Lấy khóa bí mật hiện tại.
     *
     * @return Khóa bí mật hiện tại
     */
    SecretKey getKey();

    AlgorithmParameterSpec getIvSpec();

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

    private static ISymmetricalEncrypt getInstanceSymmetricalEncrypt(Algorithms algorithm, String mode, String padding) {
        return switch (algorithm) {
            case AES -> new AESEncrypt(mode, padding);
            case AESWrap -> new AESWrapEncrypt(mode, padding);
            case AESWrapPad -> new AESWrapPadEncrypt(mode, padding);
            case ARCFOUR -> new ARCFOUREncrypt(mode, padding);
            case Blowfish -> new BlowfishEncrypt(mode, padding);
            case ChaCha20 -> new ChaCha20Encrypt(mode, padding);
            case ChaCha20Poly1305 -> new ChaCha20Poly1305Encrypt(mode, padding);
            case DES -> new DESEncrypt(mode, padding);
            case DESede -> new DESedeEncrypt(mode, padding);
            case DESedeWrap -> new DESedeWrapEncrypt(mode, padding);
            case RC2 -> new RC2Encrypt(mode, padding);
            case RC4 -> new RC4Encrypt(mode, padding);
            case RC5 -> new RC5Encrypt(mode, padding);
        };
    }

    default void saveKey(String file) throws IOException {
        var key = getKey();
        var iv = getIvSpec();
        var algorithm = key.getAlgorithm();

        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file), 1024 * 10));
        outputStream.writeUTF(algorithm);
        outputStream.writeUTF(Base64.getEncoder().encodeToString(key.getEncoded()));
        if (iv == null) {
            outputStream.writeUTF("None");
            outputStream.close();
            return;
        }
        switch (iv) {
            case IvParameterSpec spec -> {
                outputStream.writeUTF(IvParameterSpec.class.getName());
                outputStream.writeUTF(Base64.getEncoder().encodeToString(spec.getIV()));
            }
            case ChaCha20ParameterSpec spec -> {
                outputStream.writeUTF(ChaCha20ParameterSpec.class.getName());
                outputStream.writeUTF(Base64.getEncoder().encodeToString(spec.getNonce()));
                outputStream.writeInt(spec.getCounter());
            }
            case ChaCha20Poly1305Spec spec -> {
                outputStream.writeUTF(ChaCha20Poly1305Spec.class.getName());
                outputStream.writeUTF(spec.getAAD());
                outputStream.writeUTF(Base64.getEncoder().encodeToString(spec.getIv().getIV()));
            }
            default -> {
                outputStream.writeUTF("None");
            }
        }
        outputStream.close();
    }

    class KeyFactory {
        public static SymmetricalKey readKey(String file) throws IOException {
            DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file), 1024 * 10));
            String algorithm = input.readUTF();
            String keyBase64 = input.readUTF();
            AlgorithmParameterSpec iv = null;
            String nameIV = input.readUTF();
            if (nameIV.equals(IvParameterSpec.class.getName())) {
                iv = new IvParameterSpec(Base64.getDecoder().decode(input.readUTF()));
            } else if (nameIV.equals(ChaCha20ParameterSpec.class.getName())) {
                iv = new ChaCha20ParameterSpec(Base64.getDecoder().decode(input.readUTF()), input.readInt());
            } else if (nameIV.equals(ChaCha20Poly1305Spec.class.getName())) {
                iv = new ChaCha20Poly1305Spec(input.readUTF(), Base64.getDecoder().decode(input.readUTF()));
            }
            return new SymmetricalKey(new SecretKeySpec(Base64.getDecoder().decode(keyBase64), algorithm), iv);
        }
    }

    static Cipher getCipherInstance(Algorithms algorithms) throws NoSuchPaddingException, NoSuchAlgorithmException {
        return switch (algorithms) {
            case AES -> Cipher.getInstance("AES");
            case AESWrap -> Cipher.getInstance("AESWrap");
            case AESWrapPad -> Cipher.getInstance("AESWrapPad");
            case ARCFOUR -> Cipher.getInstance("ARCFOUR");
            case Blowfish -> Cipher.getInstance("Blowfish");
            case ChaCha20 -> Cipher.getInstance("ChaCha20");
            case ChaCha20Poly1305 -> Cipher.getInstance("ChaCha20-Poly1305");
            case DES -> Cipher.getInstance("DES");
            case DESede -> Cipher.getInstance("DESede");
            case DESedeWrap -> Cipher.getInstance("DESedeWrap");
            case RC2 -> Cipher.getInstance("RC2");
            case RC4 -> Cipher.getInstance("RC4");
            case RC5 -> Cipher.getInstance("RC5");
        };
    }

    static Cipher getCipherInstance(Algorithms algorithms, String mode, String padding) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        String extension = (mode == null || mode.isBlank() ? "" : "/" + mode) + (padding == null || padding.isBlank() ? "" : "/" + padding);
        return switch (algorithms) {
            case AES -> Cipher.getInstance("AES" + extension, "SunJCE");
            case AESWrap -> Cipher.getInstance("AESWrap" + extension, "SunJCE");
            case AESWrapPad -> Cipher.getInstance("AESWrapPad" + extension, "SunJCE");
            case ARCFOUR -> Cipher.getInstance("ARCFOUR" + extension, "SunJCE");
            case Blowfish -> Cipher.getInstance("Blowfish" + extension, "SunJCE");
            case ChaCha20 -> Cipher.getInstance("ChaCha20" + extension, "SunJCE");
            case ChaCha20Poly1305 -> Cipher.getInstance("ChaCha20-Poly1305" + extension, "SunJCE");
            case DES -> Cipher.getInstance("DES" + extension, "SunJCE");
            case DESede -> Cipher.getInstance("DESede" + extension, "SunJCE");
            case DESedeWrap -> Cipher.getInstance("DESedeWrap" + extension, "SunJCE");
            case RC2 -> Cipher.getInstance("RC2" + extension, "SunJCE");
            case RC4 -> Cipher.getInstance("RC4" + extension, "SunJCE");
            case RC5 -> Cipher.getInstance("RC5" + extension, "SunJCE");
        };
    }

    class Factory {
        /**
         * Hổ trợ tạo một interface mã hóa đối xứng với mode và padding.
         *
         * @param algorithm Thuật toán mã hóa đối xứng muốn tạo.
         * @param keySize   Chiều dài của khóa tương ứng với từng thuật toán.
         * @param mode      Mode của thuật toán
         * @param padding   Padding ủa thuật toán
         * @return ISymmetricalEncrypt: Một interface mã hóa đối xứng.
         * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán  mã hóa.
         * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại.
         * @throws InvalidKeyException      Lỗi key không phù hợp với thuật toán.
         */
        public static ISymmetricalEncrypt createEncrypt(Algorithms algorithm, String mode, String padding, int keySize) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
            var instance = getInstanceSymmetricalEncrypt(algorithm, mode, padding);

            instance.loadKey(new SymmetricalKey(instance.generateKey(keySize), instance.getIvSpec()));
            return instance;
        }

        /**
         * Hổ trợ tạo một interface mã hóa đối xứng với với mode và padding.
         *
         * @param algorithm Thuật toán mã hóa đối xứng muốn tạo.
         * @param key       Khóa của thuật toán.
         * @param mode      Mode của thuật toán
         * @param padding   Padding ủa thuật toán
         * @return ISymmetricalEncrypt: Một interface mã hóa đối xứng.
         * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán  mã hóa.
         * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại.
         * @throws InvalidKeyException      Lỗi key không phù hợp với thuật toán.
         */
        public static ISymmetricalEncrypt createEncrypt(Algorithms algorithm, String mode, String padding, SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
            var instance = getInstanceSymmetricalEncrypt(algorithm, mode, padding);

            instance.loadKey(key);
            return instance;
        }

        /**
         * Hổ trợ tạo một interface mã hóa đối xứng với mode và padding.
         *
         * @param algorithm Thuật toán mã hóa đối xứng muốn tạo.
         * @param keySize   Chiều dài của khóa tương ứng với từng thuật toán.
         * @param mode      Mode của thuật toán
         * @param padding   Padding ủa thuật toán
         * @return ISymmetricalEncrypt: Một interface mã hóa đối xứng.
         * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán  mã hóa.
         * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại.
         * @throws InvalidKeyException      Lỗi key không phù hợp với thuật toán.
         */
        public static ISymmetricalEncrypt createEncrypt(String algorithm, String mode, String padding, int keySize) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
            return createEncrypt(Algorithms.valueOf(algorithm), mode, padding, keySize);
        }

        /**
         * Hổ trợ tạo một interface mã hóa đối xứng với với mode và padding.
         *
         * @param algorithm Thuật toán mã hóa đối xứng muốn tạo.
         * @param key       Khóa của thuật toán.
         * @param mode      Mode của thuật toán
         * @param padding   Padding ủa thuật toán
         * @return ISymmetricalEncrypt: Một interface mã hóa đối xứng.
         * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán  mã hóa.
         * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại.
         * @throws InvalidKeyException      Lỗi key không phù hợp với thuật toán.
         */
        public static ISymmetricalEncrypt createEncrypt(String algorithm, String mode, String padding, SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
            return createEncrypt(Algorithms.valueOf(algorithm), mode, padding, key);
        }

        /**
         * Hổ trợ tạo một interface giải mã đối xứng với mode và padding.
         *
         * @param algorithm Thuật toán giải mã đối xứng muốn tạo.
         * @param key       Key để tạo cipher giải mã.
         * @param mode      Mode của thuật toán
         * @param padding   Padding ủa thuật toán
         * @return ISymmetricalDecrypt Một interface giải mã đối xứng.
         * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán  mã hóa.
         * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại.
         * @throws InvalidKeyException      Lỗi key không phù hợp với thuật toán.
         */
        public static ISymmetricalDecrypt createDecrypt(Algorithms algorithm, String mode, String padding, SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
            return switch (algorithm) {
                case AES -> new AESDecrypt(key, mode, padding);
                case AESWrap -> new AESWrapDecrypt(key, mode, padding);
                case AESWrapPad -> new AESWrapPadDecrypt(key, mode, padding);
                case ARCFOUR -> new ARCFOURDecrypt(key, mode, padding);
                case Blowfish -> new BlowfishDecrypt(key, mode, padding);
                case ChaCha20 -> new ChaCha20Decrypt(key, mode, padding);
                case ChaCha20Poly1305 -> new ChaCha20Poly1305Decrypt(key, mode, padding);
                case DES -> new DESDecrypt(key, mode, padding);
                case DESede -> new DESedeDecrypt(key, mode, padding);
                case DESedeWrap -> new DESedeWrapDecrypt(key, mode, padding);
                case RC2 -> new RC2Decrypt(key, mode, padding);
                case RC4 -> new RC4Decrypt(key, mode, padding);
                case RC5 -> new RC5Decrypt(key, mode, padding);
            };
        }

        /**
         * Hổ trợ tạo một interface giải mã đối xứng với mode và padding.
         *
         * @param algorithm Thuật toán giải mã đối xứng muốn tạo.
         * @param key       Key để tạo cipher giải mã.
         * @param mode      Mode của thuật toán
         * @param padding   Padding ủa thuật toán
         * @return ISymmetricalDecrypt Một interface giải mã đối xứng.
         * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán  mã hóa.
         * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại.
         * @throws InvalidKeyException      Lỗi key không phù hợp với thuật toán.
         */
        public static ISymmetricalDecrypt createDecrypt(String algorithm, String mode, String padding, SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
            return createDecrypt(Algorithms.valueOf(algorithm), mode, padding, key);
        }
    }

    enum Algorithms {
        AES, AESWrap, AESWrapPad, ARCFOUR, Blowfish, ChaCha20, ChaCha20Poly1305, DES, DESede, DESedeWrap, RC2, RC4, RC5,
    }
}