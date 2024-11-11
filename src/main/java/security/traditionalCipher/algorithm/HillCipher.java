/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:08 PM - 31/10/2024
 * User: lam-nguyen
 **/

package main.java.security.traditionalCipher.algorithm;

import main.java.helper.CharSetConfig;
import main.java.helper.MatrixHelper;
import main.java.security.traditionalCipher.ATraditionalCipher;
import main.java.security.traditionalCipher.ITraditionalCipher;
import main.java.security.traditionalCipher.ITraditionalCipherImpl;
import main.java.security.traditionalCipher.TraditionalKey;
import main.java.security.traditionalCipher.decrypt.HillDecrypt;
import main.java.security.traditionalCipher.encrypt.HillEncrypt;

import java.security.SecureRandom;

public class HillCipher extends ATraditionalCipher {
    private int[][] key;
    private ITraditionalCipherImpl cipher;

    public HillCipher(int[][] key, SecureLanguage lang) {
        super(lang);
        this.key = key;
    }

    public HillCipher(SecureLanguage lang) {
        super(lang);
        this.key = null;
    }

    @Override
    public void init(SecureMode mode) throws Exception {
        if (!validateKey(this.key)) throw new Exception("Key error");
        if (mode == ITraditionalCipher.SecureMode.ENCRYPT) {
            cipher = new HillEncrypt(key, CharSetConfig.getMapChar(language, SHIFT_CHAR));
        } else if (mode == ITraditionalCipher.SecureMode.DECRYPT) {
            cipher = new HillDecrypt(key, CharSetConfig.getMapChar(language, SHIFT_CHAR));
        }
    }

    @Override
    public byte[] doFinal(byte[] data) throws Exception {
        return cipher.doFinal(data);
    }

    @Override
    public String doFinal(String data) throws Exception {
        return cipher.doFinal(data);
    }

    private boolean validateKey(int[][] key) {
        if (key.length != key[0].length) return false;
        var det = MatrixHelper.detMatrix(key);
        return det != 0 && ITraditionalCipher.gcd(det, language.totalChar) == 1;
    }

    public TraditionalKey<int[][]> generateKey(int sizeKey) throws Exception {
        if (sizeKey <= 0) throw new Exception("Length key must be longer than 1!");
        var random = new SecureRandom();
        var key = new int[sizeKey][sizeKey];
        generateKeyHelper(key, sizeKey, random);
        var validate = validateKey(key);
        while (!validate) {
            generateKeyHelper(key, sizeKey, random);
            validate = validateKey(key);
        }

        return new TraditionalKey<>(key);
    }

    @Override
    public void loadKey(TraditionalKey<?> traditionalKey) throws Exception {
        try {
            this.key = (int[][]) traditionalKey.contentKey();
        } catch (Exception e) {
            throw new Exception("Key must be a int[][]!");
        }
    }

    private void generateKeyHelper(int[][] key, int sizeKey, SecureRandom random) {
        for (var row = 0; row < sizeKey; row++) {
            for (var col = 0; col < sizeKey; col++) {
                key[row][col] = Math.abs(random.nextInt(2, 300));
            }
        }
    }
}