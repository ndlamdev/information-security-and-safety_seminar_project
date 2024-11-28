/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:35 AM - 28/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher.algorithm;

import com.lamnguyen.config.CharSetConfig;
import com.lamnguyen.model.traditionalCipher.ATraditionalCipher;
import com.lamnguyen.model.traditionalCipher.ITraditionalCipherImpl;
import com.lamnguyen.model.traditionalCipher.TraditionalKey;
import com.lamnguyen.model.traditionalCipher.decrypt.VigenereDecrypt;
import com.lamnguyen.model.traditionalCipher.encrypt.VigenereEncrypt;

import java.io.*;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;


public class VigenereCipher extends ATraditionalCipher {
    private ITraditionalCipherImpl algorithm;
    private int[] key;
    private final Map<Character, Integer> mapChar = CharSetConfig.getMapChar(language, SHIFT_CHAR);

    public VigenereCipher(String key, SecureLanguage language) {
        super(new TraditionalKey<>(key), language);
        this.key = CharSetConfig.encodeStringToArrayCharEncode(key, mapChar, new HashMap<>());
    }

    public VigenereCipher(SecureLanguage language) {
        super(language);
    }

    @Override
    public void saveKey(String file) throws IOException {
        var out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        out.writeUTF(Algorithms.VIGENERE.name());
        out.writeUTF(language.name());
        out.writeUTF(CharSetConfig.decodeArrayCharEncodeToString(key, mapChar, new HashMap<>(), key.length));
        out.close();
    }

    @Override
    public TraditionalKey<?> readKey(String file) throws IOException, NumberFormatException {
        var in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        if (!Algorithms.VIGENERE.name().equals(in.readUTF()) || !language.name().equals(in.readUTF()))
            throw new IOException("Khóa Không hợp lệ");
        var key = in.readUTF();
        in.close();
        return new TraditionalKey<>(key);
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

    public TraditionalKey<String> generateTraditionalKey(String sizeKey) throws Exception {
        var size = Integer.parseInt(sizeKey);
        if (size <= 2) throw new Exception("Length key must be longer than 2!");
        StringBuilder builder = new StringBuilder();
        var random = new SecureRandom();
        var mapChar = CharSetConfig.getMapChar(language).keySet().stream().toList();
        var bound = mapChar.size();
        for (var index = 0; index < size; index++)
            builder.append(mapChar.get(random.nextInt(bound)));

        return new TraditionalKey<>(builder.toString());
    }

    @Override
    public void loadTraditionalKey(TraditionalKey<?> traditionalKey) throws Exception {
        try {
            var stringKey = (String) traditionalKey.contentKey();
            this.key = CharSetConfig.encodeStringToArrayCharEncode(stringKey, mapChar, new HashMap<>());
            this.traditionalKey = traditionalKey;
        } catch (Exception e) {
            throw new Exception("Key must be a String!");
        }
    }
}