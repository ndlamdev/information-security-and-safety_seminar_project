/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:34 AM - 29/10/2024
 * User: lam-nguyen
 **/

package main.java.security.asymmetrical.decrypt;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public class RSADecrypt extends AASymmetricalDecrypt {
    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    }
}