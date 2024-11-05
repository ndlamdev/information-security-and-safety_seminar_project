/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:40 AM - 15/10/2024
 * User: lam-nguyen
 **/
package main.java.server.security.symmetrical.decrypt;

import main.java.server.security.symmetrical.ISymmetrical;

public interface ISymmetricalDecrypt extends ISymmetrical {
    String decrypt(byte[] data);

    String decryptBase64ToString(String data);

    boolean decryptFile(String source, String dest, long skip);
}