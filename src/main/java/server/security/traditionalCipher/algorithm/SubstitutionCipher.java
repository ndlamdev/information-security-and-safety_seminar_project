/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:27 AM - 03/11/2024
 * User: lam-nguyen
 **/

package main.java.server.security.traditionalCipher.algorithm;

import main.java.server.helper.CharSetConfig;
import main.java.server.security.traditionalCipher.ATraditionalCipher;
import main.java.server.security.traditionalCipher.ITraditionalCipher;
import main.java.server.security.traditionalCipher.ITraditionalCipherImpl;
import main.java.server.security.traditionalCipher.TraditionalKey;
import main.java.server.security.traditionalCipher.decrypt.SubstitutionDecrypt;
import main.java.server.security.traditionalCipher.encrypt.SubstitutionEncrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SubstitutionCipher extends ATraditionalCipher {
    private ITraditionalCipherImpl algorithm;
    private Map<Character, Character> key;

    public SubstitutionCipher(Map<Character, Character> key, SecureLanguage language) {
        super(language);
        this.key = key;
    }

    public SubstitutionCipher(SecureLanguage language) {
        super(language);
    }

    @Override
    public void init(SecureMode mode) throws Exception {
        algorithm = switch (mode) {
            case ENCRYPT -> new SubstitutionEncrypt(key, CharSetConfig.getMapChar(language, SHIFT_CHAR));
            case DECRYPT -> new SubstitutionDecrypt(key, CharSetConfig.getMapChar(language, SHIFT_CHAR));
        };
    }

    @Override
    public byte[] doFinal(byte[] data) throws Exception {
        return algorithm.doFinal(data);
    }

    @Override
    public String doFinal(String data) throws Exception {
        return algorithm.doFinal(data);
    }

    public TraditionalKey<Map<Character, Character>> generateKey(int sizeKey) throws Exception {
        var listKey = CharSetConfig.getMapChar(ITraditionalCipher.SecureLanguage.VN).keySet().stream().toList();
        var listValue = CharSetConfig.getMapChar(ITraditionalCipher.SecureLanguage.VN, 13).entrySet().stream().toList();
        Map<Character, Character> key = new HashMap<>();
        for (var index = 0; index < listKey.size(); index++) {
            var k = listKey.get(index);
            var v = listValue.get(listValue.get(index).getValue() % listKey.size()).getKey();
            key.put(k, v);
        }
        return new TraditionalKey<>(key);
    }

    @Override
    public void loadKey(TraditionalKey<?> traditionalKey) throws Exception {
        try {
            this.key = (Map<Character, Character>) traditionalKey.contentKey();
        } catch (Exception e) {
            throw new Exception("Key must be a Map<Character, Character>!");
        }
    }
}
