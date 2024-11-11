/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:01 AM - 01/11/2024
 * User: lam-nguyen
 **/

package main.java.security.traditionalCipher.decrypt;

import main.java.helper.MatrixHelper;
import main.java.security.traditionalCipher.ATraditionalCipherImpl;
import main.java.security.traditionalCipher.encrypt.HillEncrypt;

import java.util.Map;

public class HillDecrypt extends ATraditionalCipherImpl {
    private final HillEncrypt decrypt;
    private final String splitString = new String(new byte[]{0});

    public HillDecrypt(int[][] key, Map<Character, Integer> mapChar) throws Exception {
        super(mapChar);
        var inverseKey = MatrixHelper.inverseMatrix(key, mapChar.size());
        this.decrypt = new HillEncrypt(inverseKey, mapChar);
    }

    @Override
    public byte[] doFinal(byte[] data) throws Exception {
        return decrypt.doFinal(data);
    }

    @Override
    public String doFinal(String data) throws Exception {
        var rootData = data.replace(splitString, "");
        var lengthData = data.indexOf(splitString);
        var result = decrypt.doFinal(rootData);
        if (lengthData == -1) return result;
        return result.substring(0, lengthData);
    }
}