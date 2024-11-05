/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:55 AM - 28/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.traditionalCipher.algorithm;

import main.java.server.helper.CharSetConfig;
import main.java.server.security.traditionalCipher.ATraditionalCipher;
import main.java.server.security.traditionalCipher.ITraditionalCipherImpl;
import main.java.server.security.traditionalCipher.TraditionalKey;
import main.java.server.security.traditionalCipher.decrypt.ShiftDecrypt;
import main.java.server.security.traditionalCipher.encrypt.ShiftEncrypt;

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

    public TraditionalKey<Integer> generateKey(int sizeKey) throws Exception {
        if (sizeKey <= 0) throw new Exception("Length key must be longer than 1!");
        return new TraditionalKey<>(Math.abs(new SecureRandom().nextInt(sizeKey)));
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