/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:55 AM - 28/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.traditionalCipher.encrypt;

import main.java.server.security.traditionalCipher.ATraditionalCipherImpl;

import java.util.*;

import static main.java.server.helper.CharSetConfig.decodeArrayCharEncodeToString;
import static main.java.server.helper.CharSetConfig.encodeStringToArrayCharEncode;

public class ShiftEncrypt extends ATraditionalCipherImpl {
    private final int key;

    public ShiftEncrypt(int key, Map<Character, Integer> mapChar) {
        super(mapChar);
        this.key = key;
    }

    public byte[] doFinal(byte[] data) {
        return doFinal(new String(data)).getBytes();
    }

    public String doFinal(String data) {
        Map<Integer, Character> mapCharNotEncrypt = new HashMap<>();
        var result = new int[data.length()];
        int[] arrayCharEncode = encodeStringToArrayCharEncode(data, mapChar, mapCharNotEncrypt);
        for (int i = 0; i < arrayCharEncode.length; i++) {
            result[i] = Math.floorMod(arrayCharEncode[i] + key, mapChar.size());
        }
        return decodeArrayCharEncodeToString(result, mapChar, mapCharNotEncrypt, data.length());
    }
}
