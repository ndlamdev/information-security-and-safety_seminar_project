/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.asymmetrical.encrypt;

import main.java.server.security.asymmetrical.ASymmetricalKey;
import main.java.server.security.symmetrical.ISymmetrical;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public interface IASymmetricalEncrypt {
    void loadKey(PublicKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException;

    ASymmetricalKey generateKey(int size) throws Exception;

    byte[] encrypt(String data) throws Exception;

    String encryptToBase64(byte[] data) throws Exception;

    String encryptStringToBase64(String data) throws Exception;

    boolean encryptFile(ISymmetrical.SymmetricalAlgorithm algorithm, int sizeKey, String source, String dest) throws Exception;
}