/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:27 AM - 28/10/2024
 *  User: lam-nguyen
 **/

package com.lamnguyen.security.traditionalCipher;

import java.util.Map;

public abstract class ATraditionalCipherImpl implements ITraditionalCipherImpl {
  protected final Map<Character, Integer> mapChar;

    protected ATraditionalCipherImpl(Map<Character, Integer> mapChar) {
        this.mapChar = mapChar;
    }
}

