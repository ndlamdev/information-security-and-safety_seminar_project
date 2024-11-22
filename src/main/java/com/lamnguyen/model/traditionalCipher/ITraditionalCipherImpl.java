/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:27 AM - 28/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher;

public interface ITraditionalCipherImpl {
    byte[] doFinal(byte[] data) throws Exception;

    String doFinal(String data) throws Exception;
}

