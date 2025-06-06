/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:29 AM - 03/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher.encrypt;

import java.io.IOException;
import java.util.Map;

public class SubstitutionEncrypt extends ATraditionalEncrypt {
    private final Map<Character, Character> key;

    public SubstitutionEncrypt(Map<Character, Character> key, Map<Character, Integer> mapChar) {
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
        var newCh = key.get(currentString.charAt(0));
        return newCh == null ? currentString.charAt(0) : newCh;
    }

    @Override
    public void saveKey(String fullPath) throws IOException {

    }
}