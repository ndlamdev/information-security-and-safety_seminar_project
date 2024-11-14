/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:41 PM - 31/10/2024
 * User: lam-nguyen
 **/

package main.java.security.traditionalCipher.encrypt;

import main.java.helper.MatrixHelper;
import main.java.security.traditionalCipher.ATraditionalCipherImpl;

import java.util.*;

import static main.java.config.CharSetConfig.*;

public class HillEncrypt extends ATraditionalCipherImpl {
    private final int[][] key;
    private final boolean hasPadding;

    public HillEncrypt(int[][] key, Map<Character, Integer> mapChar) {
        super(mapChar);
        this.key = key;
        hasPadding = true;
    }

    public HillEncrypt(int[][] key, Map<Character, Integer> mapChar, boolean padding) {
        super(mapChar);
        this.key = key;
        this.hasPadding = padding;
    }

    @Override
    public byte[] doFinal(byte[] data) throws Exception {
        return doFinal(new String(data)).getBytes();
    }

    @Override
    public String doFinal(String data) throws Exception {
        int lengthKey = key.length;
        Map<Integer, Character> mapCharNotEncrypt = new HashMap<>();
        int[] arrayCharCode = encodeStringToArrayCharEncode(data, mapChar, mapCharNotEncrypt);

        int totalCharEncode = arrayCharCode.length;
        int[] result = new int[totalCharEncode % lengthKey == 0 ? totalCharEncode : lengthKey * ((totalCharEncode / lengthKey) + 1)];

        doFinalHelper(result, arrayCharCode, lengthKey, totalCharEncode, data.length());

        var str = decodeArrayCharEncodeToString(result, mapChar, mapCharNotEncrypt, result.length + mapCharNotEncrypt.size());
        if (arrayCharCode.length % lengthKey == 0)
            return str;
        return str.substring(0, data.length()) + ((char) 0) + str.substring(data.length());
    }

    private void doFinalHelper(int[] result, int[] arrayCharCode, int lengthKey, int totaCharEncode, int lengthData) throws Exception {
        for (int index = 0; index < arrayCharCode.length / (double) lengthKey; index++) {
            int[] arr = Arrays.copyOfRange(arrayCharCode, index * lengthKey, Math.min((index + 1) * lengthKey, lengthData));
            if (arr.length != lengthKey)
                arr = Arrays.copyOf(arr, lengthKey);

            arr = MatrixHelper.mulArrayWithMatrixAndModulo(arr, key, mapChar.size());
            System.arraycopy(arr, 0, result, index * lengthKey, lengthKey);
            totaCharEncode -= lengthKey;
        }
    }
}
