/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:55 AM - 28/10/2024
 * User: lam-nguyen
 **/

package main.java.security.traditionalCipher.encrypt;

import main.java.security.traditionalCipher.algorithm.AffineCipher;
import main.java.security.traditionalCipher.ITraditionalCipherImpl;

import java.util.HashMap;
import java.util.Map;

import static main.java.config.CharSetConfig.decodeArrayCharEncodeToString;
import static main.java.config.CharSetConfig.encodeStringToArrayCharEncode;

public class AffineEncrypt implements ITraditionalCipherImpl {
    private final AffineCipher.AffineKey key;
    private final Map<Character, Integer> mapChar;

    public AffineEncrypt(AffineCipher.AffineKey key, Map<Character, Integer> mapChar) {
        this.key = key;
        this.mapChar = mapChar;
    }

    @Override
    public byte[] doFinal(byte[] data) {
        return doFinal(new String(data)).getBytes();
    }

    @Override
    public String doFinal(String data) {
        Map<Integer, Character> mapCharNotEncrypt = new HashMap<>();
        var result = new int[data.length()];
        int[] arrayCharEncode = encodeStringToArrayCharEncode(data, mapChar, mapCharNotEncrypt);
        for (int i = 0; i < arrayCharEncode.length; i++) {
            result[i] = Math.floorMod(arrayCharEncode[i] * key.a() + key.b(), mapChar.size());
        }
        return decodeArrayCharEncodeToString(result, mapChar, mapCharNotEncrypt, data.length());
    }
}