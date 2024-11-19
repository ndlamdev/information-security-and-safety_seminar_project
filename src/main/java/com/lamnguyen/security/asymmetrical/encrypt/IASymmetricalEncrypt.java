/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.security.asymmetrical.encrypt;

import com.lamnguyen.security.asymmetrical.AsymmetricalKey;
import com.lamnguyen.security.symmetrical.ISymmetrical;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public interface IASymmetricalEncrypt {
    void loadKey(PublicKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException;

    AsymmetricalKey generateKey(int size) throws NoSuchAlgorithmException;

    byte[] encrypt(String data) throws IllegalBlockSizeException, BadPaddingException;

    String encryptToBase64(byte[] data) throws IllegalBlockSizeException, BadPaddingException;

    String encryptStringToBase64(String data) throws IllegalBlockSizeException, BadPaddingException;

    void encryptFile(ISymmetrical.Algorithms algorithm, int sizeKey, String source, String dest) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException;
}