/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:40 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.symmetrical.encrypt;

import main.java.server.security.symmetrical.ISymmetrical;

import javax.crypto.SecretKey;

public interface ISymmetricalEncrypt extends ISymmetrical {
    SecretKey generateKey(int size);

    byte[] encrypt(String data);

    String encryptStringBase64(String data);

    boolean encryptFile(String source, String dest, boolean append);
}