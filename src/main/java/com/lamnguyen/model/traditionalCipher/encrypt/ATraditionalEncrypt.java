/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:26 AM - 17/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher.encrypt;

import com.lamnguyen.model.traditionalCipher.ATraditionalCipherImpl;

import java.io.IOException;
import java.util.Map;

public abstract class ATraditionalEncrypt extends ATraditionalCipherImpl {
    protected ATraditionalEncrypt(Map<Character, Integer> mapChar) {
        super(mapChar);
    }

    public abstract void saveKey(String fullPath) throws IOException;
}
