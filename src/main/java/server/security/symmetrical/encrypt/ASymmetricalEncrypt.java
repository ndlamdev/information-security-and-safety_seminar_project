/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:46 AM - 15/10/2024
 *  User: lam-nguyen
 **/

package main.java.server.security.symmetrical.encrypt;

import main.java.server.security.symmetrical.ASymmetrical;

import javax.crypto.CipherInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

public abstract class ASymmetricalEncrypt extends ASymmetrical implements ISymmetricalEncrypt {

    @Override
    public byte[] encrypt(String data) {
        try {
            return cipher.doFinal(data.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encryptStringBase64(String data) {
        return Base64.getEncoder().encodeToString(encrypt(data));
    }

    @Override
    public boolean encryptFile(String source, String dest, boolean append) {
        try (BufferedInputStream bufferInput = new BufferedInputStream(new FileInputStream(source));
            CipherInputStream input = new CipherInputStream(bufferInput, cipher);
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(dest, append))) {

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
                e.printStackTrace();
                return false;
            }
            return true;
        }
}