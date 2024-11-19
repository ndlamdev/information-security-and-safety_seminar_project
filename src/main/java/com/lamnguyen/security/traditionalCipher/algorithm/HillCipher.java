/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:08 PM - 31/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.security.traditionalCipher.algorithm;

import com.lamnguyen.config.CharSetConfig;
import com.lamnguyen.helper.MatrixHelper;
import com.lamnguyen.security.traditionalCipher.ATraditionalCipher;
import com.lamnguyen.security.traditionalCipher.ITraditionalCipher;
import com.lamnguyen.security.traditionalCipher.ITraditionalCipherImpl;
import com.lamnguyen.security.traditionalCipher.TraditionalKey;
import com.lamnguyen.security.traditionalCipher.decrypt.HillDecrypt;
import com.lamnguyen.security.traditionalCipher.encrypt.HillEncrypt;

import java.io.*;
import java.security.SecureRandom;
import java.util.Arrays;

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
    public void saveKey(String file) throws IOException {
        var out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        out.writeUTF(Algorithms.HILL.name());
        out.writeUTF(language.name());
        out.writeInt(key.length);
        for (var row : key) {
            out.writeUTF(String.join("_", Arrays.stream(row).mapToObj(String::valueOf).toArray(String[]::new)));
        }
        out.close();
    }

    @Override
    public TraditionalKey<?> readKey(String file) throws IOException, NumberFormatException {
        var in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        if (!Algorithms.HILL.name().equals(in.readUTF()) || !language.name().equals(in.readUTF()))
            throw new IOException("Khóa Không hợp lệ");
        var size = in.readInt();
        var key = new int[size][size];
        for (var row = 0; row < size; row++) {
            var rowString = in.readUTF();
            var rowSplit = rowString.split("_");
            for (var cell = 0; cell < size; cell++)
                key[row][cell] = Integer.parseInt(rowSplit[cell]);
        }
        in.close();
        return new TraditionalKey<>(key);
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

    public TraditionalKey<int[][]> generateKey(String sizeKey) throws Exception {
        var size = Integer.parseInt(sizeKey);
        if (size <= 0) throw new Exception("Length key must be longer than 1!");
        var random = new SecureRandom();
        var key = new int[size][size];
        generateKeyHelper(key, size, random);
        var validate = validateKey(key);
        while (!validate) {
            generateKeyHelper(key, size, random);
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