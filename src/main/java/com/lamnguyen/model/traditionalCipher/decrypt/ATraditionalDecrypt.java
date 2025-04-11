/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:26 AM - 17/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher.decrypt;

import com.lamnguyen.model.traditionalCipher.ATraditionalCipherImpl;
import com.lamnguyen.model.traditionalCipher.TraditionalKey;

import java.util.Map;

public abstract class ATraditionalDecrypt extends ATraditionalCipherImpl {
    protected ATraditionalDecrypt(TraditionalKey<?> key, Map<Character, Integer> mapChar) {
        super(mapChar);
    }
}
