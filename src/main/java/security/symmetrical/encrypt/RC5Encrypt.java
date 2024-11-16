/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:14 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.security.symmetrical.encrypt;

import lombok.NoArgsConstructor;
import main.java.security.symmetrical.ISymmetrical;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor
public class RC5Encrypt extends ASymmetricalEncrypt {
    public RC5Encrypt(String mode, String padding, IvParameterSpec iv) {
        super(mode, padding, iv);
    }

    public RC5Encrypt(String mode, String padding) {
        super(mode, padding);
    }

    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = ISymmetrical.getCipherInstance(Algorithms.RC5, mode, padding);
    }

    /**
     * Tạo đối tượng KeyGenerator
     *
     * @return KeyGenerator
     * @serialData Size key support:  0 - 2040, phổ biến là 128
     */
    @Override
    protected KeyGenerator initKeyGenerator() throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance("RC5");
    }
}