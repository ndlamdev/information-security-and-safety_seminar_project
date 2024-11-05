/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:14 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.symmetrical.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class DESEncrypt extends ASymmetricalEncrypt {

    @Override
    public SecretKey generateKey(int size) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(size);
            SecretKey key = keyGen.generateKey();
            loadKey(key);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance("DES");
    }
}