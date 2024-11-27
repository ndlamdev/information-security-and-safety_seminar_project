/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:56 AM - 28/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher.algorithm;

import com.lamnguyen.config.CharSetConfig;
import com.lamnguyen.model.traditionalCipher.ATraditionalCipher;
import com.lamnguyen.model.traditionalCipher.ITraditionalCipher;
import com.lamnguyen.model.traditionalCipher.ITraditionalCipherImpl;
import com.lamnguyen.model.traditionalCipher.TraditionalKey;
import com.lamnguyen.model.traditionalCipher.decrypt.AffineDecrypt;
import com.lamnguyen.model.traditionalCipher.encrypt.AffineEncrypt;

import java.io.*;
import java.security.SecureRandom;

public class AffineCipher extends ATraditionalCipher {
    private ITraditionalCipherImpl algorithm;
    private AffineKey key;

    public AffineCipher(AffineKey key, SecureLanguage lang) {
        super(new TraditionalKey<>(key), lang);
        this.key = key;
    }

    public AffineCipher(SecureLanguage lang) {
        super(lang);
    }

    @Override
    public void saveKey(String file) throws IOException {
        var out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        out.writeUTF(Algorithms.VIGENERE.name());
        out.writeUTF(language.name());
        out.writeUTF(key.toString());
        out.close();
    }

    @Override
    public TraditionalKey<?> readKey(String file) throws IOException, NumberFormatException {
        var in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        if (!Algorithms.VIGENERE.name().equals(in.readUTF()) || !language.name().equals(in.readUTF()))
            throw new IOException("Khóa Không hợp lệ");
        var keyString = in.readUTF();
        in.close();
        var arr = keyString.split("_");
        var a = Integer.parseInt(arr[0]);
        var b = Integer.parseInt(arr[1]);
        return new TraditionalKey<>(new AffineKey(a, b));
    }

    @Override
    public void init(SecureMode mode) throws Exception {
        if (ITraditionalCipher.gcd(key.a, language.totalChar) != 1) throw new Exception("Key Error");
        switch (mode) {
            case ENCRYPT:
                algorithm = new AffineEncrypt(key, CharSetConfig.getMapChar(language, SHIFT_CHAR));
                break;
            case DECRYPT:
                algorithm = new AffineDecrypt(key, CharSetConfig.getMapChar(language, SHIFT_CHAR));
                break;
        }
    }

    @Override
    public byte[] doFinal(byte[] data) throws Exception {
        return algorithm.doFinal(data);
    }

    @Override
    public String doFinal(String data) throws Exception {
        return algorithm.doFinal(data);
    }

    public TraditionalKey<AffineKey> generateKey(String sizeKey) throws Exception {
        var arr = sizeKey.split("_");
        var a = Integer.parseInt(arr[0]);
        var b = Integer.parseInt(arr[1]);
        if (a < 10 || b < 10) throw new Exception("size key must be longer than 10!");
        var random = new SecureRandom();
        var key = new AffineKey(Math.abs(random.nextInt(2, a)), Math.abs(random.nextInt(2, b)));
        while (key.a == key.b || ITraditionalCipher.gcd(key.a, language.totalChar) != 1)
            key = new AffineKey(Math.abs(random.nextInt(2, a)), Math.abs(random.nextInt(2, b)));
        return new TraditionalKey<>(key);
    }

    @Override
    public void loadTraditionalKey(TraditionalKey<?> traditionalKey) throws Exception {
        try {
            this.key = (AffineKey) traditionalKey.contentKey();
            this.traditionalKey = traditionalKey;
        } catch (Exception e) {
            throw new Exception("Key must be a AffineKey!");
        }
    }

    public record AffineKey(int a, int b) {
        @Override
        public String toString() {
            return a + "_" + b;
        }
    }
}

