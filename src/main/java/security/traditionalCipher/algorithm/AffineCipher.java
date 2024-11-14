/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:56 AM - 28/10/2024
 * User: lam-nguyen
 **/

package main.java.security.traditionalCipher.algorithm;

import main.java.config.CharSetConfig;
import main.java.security.traditionalCipher.ATraditionalCipher;
import main.java.security.traditionalCipher.ITraditionalCipher;
import main.java.security.traditionalCipher.ITraditionalCipherImpl;
import main.java.security.traditionalCipher.TraditionalKey;
import main.java.security.traditionalCipher.decrypt.AffineDecrypt;
import main.java.security.traditionalCipher.encrypt.AffineEncrypt;

import java.security.SecureRandom;

public class AffineCipher extends ATraditionalCipher {
    private ITraditionalCipherImpl algorithm;
    private AffineKey key;

    public AffineCipher(AffineKey key, SecureLanguage lang) {
        super(lang);
        this.key = key;
    }

    public AffineCipher(SecureLanguage lang) {
        super(lang);
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

    public TraditionalKey<AffineKey> generateKey(int sizeKey) throws Exception {
        if (sizeKey <= 0) throw new Exception("Length key must be longer than 1!");
        var random = new SecureRandom();
        var key = new AffineKey(Math.abs(random.nextInt(1, sizeKey)), Math.abs(random.nextInt(1, sizeKey)));
        while (ITraditionalCipher.gcd(key.a, language.totalChar) != 1)
            key = new AffineKey(Math.abs(random.nextInt(1, sizeKey)), Math.abs(random.nextInt(1, sizeKey)));
        return new TraditionalKey<>(key);
    }

    @Override
    public void loadKey(TraditionalKey<?> traditionalKey) throws Exception {
        try {
            this.key = (AffineKey) traditionalKey.contentKey();
        } catch (Exception e) {
            throw new Exception("Key must be a AffineKey!");
        }
    }

    public record AffineKey(int a, int b) {
    }
}

