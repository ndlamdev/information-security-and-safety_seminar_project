/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:24 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.symmetrical.decrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class DESDecrypt extends ASymmetricalDecrypt {

    public DESDecrypt() {
    }

    public DESDecrypt(SecretKey key) {
        loadKey(key);
    }

    @Override
    public void loadKey(SecretKey key) {
        super.loadKey(key);
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            System.out.println("Lỗi key không hợp lệ!");
        }
    }
}