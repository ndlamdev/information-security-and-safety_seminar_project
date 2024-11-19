/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:27 AM - 03/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.security.traditionalCipher.algorithm;

import com.lamnguyen.config.CharSetConfig;
import com.lamnguyen.security.traditionalCipher.ATraditionalCipher;
import com.lamnguyen.security.traditionalCipher.ITraditionalCipher;
import com.lamnguyen.security.traditionalCipher.ITraditionalCipherImpl;
import com.lamnguyen.security.traditionalCipher.TraditionalKey;
import com.lamnguyen.security.traditionalCipher.decrypt.SubstitutionDecrypt;
import com.lamnguyen.security.traditionalCipher.encrypt.SubstitutionEncrypt;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
    public void saveKey(String file) throws IOException {
        var out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        out.writeUTF(Algorithms.SUBSTITUTION.name());
        out.writeUTF(language.name());
        out.writeInt(key.size());
        for (Map.Entry<Character, Character> entry : key.entrySet())
            out.writeUTF(entry.getKey() + "_" + entry.getValue());
        out.close();
    }

    @Override
    public TraditionalKey<?> readKey(String file) throws IOException, NumberFormatException {
        var in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        if (!Algorithms.SUBSTITUTION.name().equals(in.readUTF()) || !language.name().equals(in.readUTF()))
            throw new IOException("Khóa Không hợp lệ");
        var size = in.readInt();
        var key = new HashMap<Character, Character>();
        for (var row = 0; row < size; row++) {
            var rowString = in.readUTF();
            var rowSplit = rowString.split("_");
            key.put(rowSplit[0].charAt(0), rowSplit[1].charAt(0));
        }
        in.close();
        return new TraditionalKey<>(key);
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

    public TraditionalKey<Map<Character, Character>> generateKey(String sizeKey) throws Exception {
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
