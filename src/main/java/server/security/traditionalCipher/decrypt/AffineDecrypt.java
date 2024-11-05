/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:55 AM - 28/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.traditionalCipher.decrypt;

import main.java.server.security.traditionalCipher.ATraditionalCipherImpl;
import main.java.server.security.traditionalCipher.algorithm.AffineCipher;
import main.java.server.security.traditionalCipher.ITraditionalCipher;

import java.util.HashMap;
import java.util.Map;

import static main.java.server.helper.CharSetConfig.decodeArrayCharEncodeToString;
import static main.java.server.helper.CharSetConfig.encodeStringToArrayCharEncode;

public class AffineDecrypt extends ATraditionalCipherImpl {
    private final AffineCipher.AffineKey key;
    private int inverseA;

    public AffineDecrypt(AffineCipher.AffineKey key, Map<Character, Integer> mapChar) {
        super(mapChar);
        this.key = key;
        this.inverseA = ITraditionalCipher.inverse(key.a(), mapChar.size());
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
            result[i] = Math.floorMod(inverseA * (arrayCharEncode[i] - key.b()), mapChar.size());
        }
        return decodeArrayCharEncodeToString(result, mapChar, mapCharNotEncrypt, data.length());
    }
}