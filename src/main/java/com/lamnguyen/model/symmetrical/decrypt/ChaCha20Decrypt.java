/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:14 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical.decrypt;

import com.lamnguyen.model.symmetrical.ISymmetrical;
import com.lamnguyen.model.symmetrical.SymmetricalKey;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ChaCha20Decrypt extends ASymmetricalDecrypt {
    public ChaCha20Decrypt(SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        super(key);
    }

    public ChaCha20Decrypt(SymmetricalKey key, String mode, String padding) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        super(key, mode, padding);
    }

    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = ISymmetrical.getCipherInstance(Algorithms.ChaCha20, mode, padding);
    }
}