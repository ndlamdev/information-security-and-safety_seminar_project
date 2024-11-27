/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.asymmetrical.decrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;

public interface IASymmetricalDecrypt {
    void loadKey(PrivateKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException;

    byte[] decrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException;

    String decryptToString(byte[] data) throws IllegalBlockSizeException, BadPaddingException;

    String decryptBase64ToString(String data) throws IllegalBlockSizeException, BadPaddingException;

    void decryptFile(String source, String dest) throws AASymmetricalDecrypt.HeaderException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, IOException, BadPaddingException, NoSuchProviderException;

    class HeaderException extends Exception {

        public HeaderException(String message) {
            super(message);
        }
    }
}