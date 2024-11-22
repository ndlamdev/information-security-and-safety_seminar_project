/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:14 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical.encrypt;

import com.lamnguyen.model.symmetrical.ISymmetrical;
import lombok.NoArgsConstructor;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor
public class ChaCha20Poly1305Encrypt extends ASymmetricalEncrypt {
    public ChaCha20Poly1305Encrypt(String mode, String padding) {
        super(mode, padding);
    }

    public ChaCha20Poly1305Encrypt(String mode, String padding, IvParameterSpec iv) {
        super(mode, padding, iv);
    }

    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = ISymmetrical.getCipherInstance(Algorithms.ChaCha20Poly1305, mode, padding);
    }


    /**
     * Tạo đối tượng KeyGenerator
     *
     * @return KeyGenerator
     * @serialData Size key support: 256
     */
    @Override
    protected KeyGenerator initKeyGenerator() throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance("ChaCha20");
    }
}