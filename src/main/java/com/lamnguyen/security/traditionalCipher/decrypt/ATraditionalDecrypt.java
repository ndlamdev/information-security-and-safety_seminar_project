/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:26 AM - 17/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.security.traditionalCipher.decrypt;

import com.lamnguyen.security.traditionalCipher.ATraditionalCipherImpl;

import java.util.Map;

public abstract class ATraditionalDecrypt extends ATraditionalCipherImpl {
    protected ATraditionalDecrypt(Map<Character, Integer> mapChar) {
        super(mapChar);
    }
}
