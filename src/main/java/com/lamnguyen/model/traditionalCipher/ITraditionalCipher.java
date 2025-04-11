/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:27 AM - 28/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher;

import com.lamnguyen.config.CharSetConfig;
import com.lamnguyen.model.traditionalCipher.algorithm.*;

import java.io.IOException;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public interface ITraditionalCipher extends ITraditionalCipherImpl {
    int SHIFT_CHAR = 0;

    /**
     * Khởi tạo mode cho thuật toán
     *
     * @param mode SecureMode mode được dùng.
     */
    void init(SecureMode mode) throws Exception;

    TraditionalKey<?> generateKey(String size) throws Exception;

    /**
     * Load khóa cổ diển cho thuận toán.
     *
     * @param traditionalKey TraditionalKey khóa cổ điển dùng để load vào thuật toán.
     */
    void loadTraditionalKey(TraditionalKey<?> traditionalKey) throws Exception;

    /**
     * Lưu khóa của điển của vào file
     *
     * @param file String đường dẫn đến file đích lưu khóa
     */
    void saveKey(String file) throws IOException;

    /**
     * Đọc khóa cổ điển từ file
     *
     * @param file String đường đãn đến file lưu khóa
     * @return TraditionalKey khóa cổ điển đọc được từ file.
     */
    TraditionalKey<?> readKey(String file) throws IOException;

    /**
     * Enum các mode mà phần mềm hổ trợ
     */
    enum SecureMode {
        DECRYPT, ENCRYPT
    }

    /**
     * Enum các ngôn ngữ mà phần mềm hổ trợ
     */
    enum SecureLanguage {
        VN(CharSetConfig.VIET_NAMES_CHAR.length()),
        EN(CharSetConfig.ENGLISH_CHAR.length());

        public final int totalChar;

        SecureLanguage(int totalChar) {
            this.totalChar = totalChar;
        }
    }

    static int inverse(int n, int modNumber) {
        return inverseHelper(n, modNumber, modNumber - 1);
    }

    private static int inverseHelper(int n, int modNumber, int index) {
        if (index == 0) return 0;

        int result = Math.floorMod(index * n, modNumber);
        if (result == 1) return index;

        return inverseHelper(n, modNumber, index - 1);
    }

    static int gcd(int a, int b) {
        return gcdHelper(a, b, Math.min(abs(a), abs(b)));
    }

    private static int gcdHelper(int a, int b, int n) {
        if (n <= 1) return 1;

        if (Math.floorMod(a, n) == 0 && Math.floorMod(b, n) == 0) return max(n, gcdHelper(a, b, n - 1));
        return gcdHelper(a, b, n - 1);
    }

    /**
     * Các thuật toán mã hóa cổ điển mà phần mềm hổ trợ
     */
    enum Algorithms {
        HILL, SHIFT, SUBSTITUTION, VIGENERE, AFFINE
    }

    class KeyFactory {

        /**
         * Hổ trợ tạo khi từ enum thuật toán, enum ngôn ngữ và chuổi size key
         *
         * @param alg  Algorithms enum thuật toán muốn tạo khóa
         * @param lang SecureLanguage enum ngôn ngữ muốn tạo khóa
         * @param size String chuổi để xác định kích thước khóa
         * @return TraditionalKey Khóa cổ điển được tạo ra.
         */
        public static TraditionalKey<?> generateKey(Algorithms alg, SecureLanguage lang, String size) throws Exception {
            var cipher = Factory.createEncrypt(alg, lang);
            return cipher.generateKey(size);
        }
    }

    /**
     * Lớp hổ trợ việc tạo đối tượng mã hóa.
     */
    class Factory {
        public static ITraditionalCipher createEncrypt(Algorithms alg, SecureLanguage lang) {
            return switch (alg) {
                case HILL -> new HillCipher(lang);
                case SHIFT -> new ShiftCipher(lang);
                case SUBSTITUTION -> new SubstitutionCipher(lang);
                case VIGENERE -> new VigenereCipher(lang);
                case AFFINE -> new AffineCipher(lang);
            };
        }
    }
}
