/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:55 AM - 28/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.security.traditionalCipher.algorithm;

import com.lamnguyen.config.CharSetConfig;
import com.lamnguyen.security.traditionalCipher.ATraditionalCipher;
import com.lamnguyen.security.traditionalCipher.ITraditionalCipherImpl;
import com.lamnguyen.security.traditionalCipher.TraditionalKey;
import com.lamnguyen.security.traditionalCipher.decrypt.ShiftDecrypt;
import com.lamnguyen.security.traditionalCipher.encrypt.ShiftEncrypt;

import java.io.*;
import java.security.SecureRandom;


public class ShiftCipher extends ATraditionalCipher {
    private ITraditionalCipherImpl algorithm;
    private int key;

    public ShiftCipher(int key, SecureLanguage language) {
        super(language);
        this.key = key;
    }

    public ShiftCipher(SecureLanguage language) {
        super(language);
    }

    @Override
    public void saveKey(String file) throws IOException {
        var out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        out.writeUTF(Algorithms.SHIFT.name());
        out.writeUTF(language.name());
        out.writeInt(key);
        out.close();
    }

    @Override
    public TraditionalKey<?> readKey(String file) throws IOException, NumberFormatException {
        var in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        if (!Algorithms.SHIFT.name().equals(in.readUTF()) || !language.name().equals(in.readUTF()))
            throw new IOException("Khóa Không hợp lệ");
        var key = in.readInt();
        in.close();
        return new TraditionalKey<>(key);
    }

    public void init(SecureMode mode) {
        algorithm = switch (mode) {
            case ENCRYPT -> new ShiftEncrypt(key, CharSetConfig.getMapChar(language, SHIFT_CHAR));
            case DECRYPT -> new ShiftDecrypt(key, CharSetConfig.getMapChar(language, SHIFT_CHAR));
        };
    }

    public byte[] doFinal(byte[] data) throws Exception {
        return algorithm.doFinal(data);
    }

    public String doFinal(String data) throws Exception {
        return algorithm.doFinal(data);
    }

    public TraditionalKey<Integer> generateKey(String sizeKey) throws Exception {
        var size = Integer.parseInt(sizeKey);
        if (size <= 2) throw new Exception("Length key must be longer than 2!");
        return new TraditionalKey<>(Math.abs(new SecureRandom().nextInt(2, size)));
    }

    @Override
    public void loadKey(TraditionalKey<?> traditionalKey) throws Exception {
        try {
            this.key = (int) traditionalKey.contentKey();
        } catch (Exception e) {
            throw new Exception("Key must be a int!");
        }
    }
}