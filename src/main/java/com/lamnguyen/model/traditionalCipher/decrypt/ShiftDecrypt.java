/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:55 AM - 28/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher.decrypt;

import java.util.HashMap;
import java.util.Map;

import static com.lamnguyen.config.CharSetConfig.decodeArrayCharEncodeToString;
import static com.lamnguyen.config.CharSetConfig.encodeStringToArrayCharEncode;

public class ShiftDecrypt extends ATraditionalDecrypt {
    private final int key;

    public ShiftDecrypt(int key, Map<Character, Integer> mapChar) {
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
            result[i] = Math.floorMod(arrayCharEncode[i] - key, mapChar.size());
        }
        return decodeArrayCharEncodeToString(result, mapChar, mapCharNotEncrypt, data.length());
    }
}
