/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:14 AM - 28/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical.encrypt;

import com.lamnguyen.model.symmetrical.ISymmetrical;
import lombok.NoArgsConstructor;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@NoArgsConstructor
public class RC6Encrypt extends ASymmetricalEncrypt {
    public RC6Encrypt(String mode, String padding, IvParameterSpec iv) {
        super(mode, padding, iv);
    }

    public RC6Encrypt(String mode, String padding) {
        super(mode, padding);
    }

    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        cipher = ISymmetrical.getCipherInstance(Algorithms.RC5, mode, padding);
    }

    /**
     * Tạo đối tượng KeyGenerator
     *
     * @return KeyGenerator
     * @serialData Size key support: 128, 192, 256
     */
    @Override
    protected KeyGenerator initKeyGenerator() throws NoSuchAlgorithmException, NoSuchProviderException {
        return KeyGenerator.getInstance(Algorithms.RC5.name(), "BC");
    }
}