/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:40 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.symmetrical.decrypt;

import main.java.server.security.symmetrical.ASymmetrical;

import javax.crypto.CipherInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

public abstract class ASymmetricalDecrypt extends ASymmetrical implements ISymmetricalDecrypt {

    @Override
    public String decrypt(byte[] data) {
        try {
            return new String(cipher.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String decryptBase64ToString(String data) {
        return decrypt(Base64.getDecoder().decode(data));
    }

    @Override
    public boolean decryptFile(String source, String dest, long skip) {
        try (BufferedInputStream bufferInput = new BufferedInputStream(new FileInputStream(source));
             CipherInputStream input = new CipherInputStream(bufferInput, cipher);
             BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(dest))) {

            bufferInput.skip(skip);
            byte[] buffer = new byte[1024 * 10];
            int i;
            while ((i = input.read(buffer)) != -1) {
                output.write(buffer, 0, i);
            }

            byte[] finalBuffer = cipher.doFinal();
            if (finalBuffer != null) {
                output.write(finalBuffer);
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}