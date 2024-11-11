/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:29 AM - 03/11/2024
 * User: lam-nguyen
 **/

package main.java.security.traditionalCipher.decrypt;

import main.java.security.traditionalCipher.ATraditionalCipherImpl;

import java.util.Map;

public class SubstitutionDecrypt extends ATraditionalCipherImpl {
    private final Map<Character, Character> key;

    public SubstitutionDecrypt(Map<Character, Character> key, Map<Character, Integer> mapChar) {
        super(mapChar);
        this.key = key;
    }

    @Override
    public byte[] doFinal(byte[] data) throws Exception {
        return doFinal(new String(data)).getBytes();
    }

    @Override
    public String doFinal(String data) throws Exception {
        StringBuilder builder = new StringBuilder();
        for (String s : data.split(""))
            builder.append(apply(s));
        return builder.toString();
    }

    private Character apply(String currentString) {
        var entryChar = key.entrySet().stream().filter(entry -> entry.getValue() == currentString.charAt(0)).findFirst().orElse(null);
        return entryChar == null ? currentString.charAt(0) : entryChar.getKey();
    }
}