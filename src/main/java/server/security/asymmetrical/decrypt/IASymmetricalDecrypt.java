/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.asymmetrical.decrypt;

import java.security.PrivateKey;

public interface IASymmetricalDecrypt {
    void loadKey(PrivateKey key) throws Exception;

    byte[] decrypt(byte[] data) throws Exception;

    String decryptToString(byte[] data) throws Exception;

    String decryptBase64ToString(String data) throws Exception;

    boolean decryptFile(String source, String dest) throws Exception;
}