/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:55 AM - 28/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher.decrypt;

import com.lamnguyen.model.traditionalCipher.ITraditionalCipher;
import com.lamnguyen.model.traditionalCipher.TraditionalKey;
import com.lamnguyen.model.traditionalCipher.algorithm.AffineCipher;

import java.util.HashMap;
import java.util.Map;

import static com.lamnguyen.config.CharSetConfig.decodeArrayCharEncodeToString;
import static com.lamnguyen.config.CharSetConfig.encodeStringToArrayCharEncode;

public class AffineDecrypt extends ATraditionalDecrypt {
    private final AffineCipher.AffineKey affineKey;
    private int inverseA;

    public AffineDecrypt(AffineCipher.AffineKey key, Map<Character, Integer> mapChar) {
        super(new TraditionalKey<>(key), mapChar);
        this.affineKey = key;
        this.inverseA = ITraditionalCipher.inverse(affineKey.a(), mapChar.size());
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
            result[i] = Math.floorMod(inverseA * (arrayCharEncode[i] - affineKey.b()), mapChar.size());
        }
        return decodeArrayCharEncodeToString(result, mapChar, mapCharNotEncrypt, data.length());
    }
}