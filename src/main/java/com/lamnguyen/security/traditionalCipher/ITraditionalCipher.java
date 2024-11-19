/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:27 AM - 28/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.security.traditionalCipher;

import com.lamnguyen.config.CharSetConfig;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public interface ITraditionalCipher extends ITraditionalCipherImpl {
    int SHIFT_CHAR = 0;

    void init(SecureMode mode) throws Exception;

    TraditionalKey<?> generateKey(int size) throws Exception;

    void loadKey(TraditionalKey<?> traditionalKey) throws Exception;

    enum SecureMode {
        DECRYPT, ENCRYPT
    }

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

    class KeyFactory {
        public enum Algorithms {
            HILL, SHIFT, SUBSTITUTION, VIGENERE, AFFINE
        }
    }
}
