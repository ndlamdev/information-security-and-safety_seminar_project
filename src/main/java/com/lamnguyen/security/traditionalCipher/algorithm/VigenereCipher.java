/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:35 AM - 28/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.security.traditionalCipher.algorithm;

import com.lamnguyen.config.CharSetConfig;
import com.lamnguyen.security.traditionalCipher.ATraditionalCipher;
import com.lamnguyen.security.traditionalCipher.ITraditionalCipherImpl;
import com.lamnguyen.security.traditionalCipher.TraditionalKey;
import com.lamnguyen.security.traditionalCipher.decrypt.VigenereDecrypt;
import com.lamnguyen.security.traditionalCipher.encrypt.VigenereEncrypt;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;


public class VigenereCipher extends ATraditionalCipher {
    private ITraditionalCipherImpl algorithm;
    private int[] key;
    private final Map<Character, Integer> mapChar = CharSetConfig.getMapChar(language, SHIFT_CHAR);

    public VigenereCipher(String key, SecureLanguage language) {
        super(language);
        this.key = CharSetConfig.encodeStringToArrayCharEncode(key, mapChar, new HashMap<>());
    }

    public VigenereCipher(SecureLanguage language) {
        super(language);

    }

    public void init(SecureMode mode) {
        algorithm = switch (mode) {
            case DECRYPT -> new VigenereDecrypt(key, mapChar);
            case ENCRYPT -> new VigenereEncrypt(key, mapChar);
        };
    }

    public byte[] doFinal(byte[] data) throws Exception {
        return algorithm.doFinal(data);
    }

    public String doFinal(String data) throws Exception {
        return algorithm.doFinal(data);
    }

    public TraditionalKey<String> generateKey(int sizeKey) throws Exception {
        if (sizeKey <= 0) throw new Exception("Length key must be longer than 1!");
        StringBuilder builder = new StringBuilder();
        var random = new SecureRandom();
        var mapChar = CharSetConfig.getMapChar(language).keySet().stream().toList();
        var bound = mapChar.size();
        for (var index = 0; index < sizeKey; index++)
            builder.append(mapChar.get(random.nextInt(bound)));

        return new TraditionalKey<>(builder.toString());
    }

    @Override
    public void loadKey(TraditionalKey<?> traditionalKey) throws Exception {
        try {
            var stringKey = (String) traditionalKey.contentKey();
            this.key = CharSetConfig.encodeStringToArrayCharEncode(stringKey, mapChar, new HashMap<>());
        } catch (Exception e) {
            throw new Exception("Key must be a String!");
        }
    }
}