/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:14 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical.encrypt;

import com.lamnguyen.model.symmetrical.ChaCha20Poly1305Spec;
import com.lamnguyen.model.symmetrical.ISymmetrical;
import com.lamnguyen.model.symmetrical.SymmetricalKey;
import com.lamnguyen.utils.IVUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.AlgorithmParameterSpec;

@NoArgsConstructor
public class ChaCha20Poly1305Encrypt extends ASymmetricalEncrypt {
    public ChaCha20Poly1305Encrypt(String mode, String padding) {
        super(mode, padding);
    }

    public ChaCha20Poly1305Encrypt(String mode, String padding, IvParameterSpec iv) {
        super(mode, padding, iv);
    }

    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
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
        ivSpec = IVUtil.generateChaCha20Poly1305SpecIV(12);
        return KeyGenerator.getInstance(Algorithms.ChaCha20.name());
    }

    @Override
    public void loadKey(SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
        this.key = key.key();
        this.ivSpec = key.iv();
        initCipher();
        try {
            var iv = (ChaCha20Poly1305Spec) ivSpec;
            cipher.init(Cipher.ENCRYPT_MODE, key.key(), iv.getIv());
            cipher.updateAAD(iv.getAAD().getBytes());
        } catch (ClassCastException e) {
            throw new InvalidKeyException();
        }
    }
}