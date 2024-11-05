/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:14 AM - 15/10/2024
 *  User: lam-nguyen
 **/

package main.java.server.security.symmetrical.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AESEncrypt extends ASymmetricalEncrypt {

    @Override
    public SecretKey generateKey(int size) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
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
    public void loadKey(SecretKey key) {
        super.loadKey(key);
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}