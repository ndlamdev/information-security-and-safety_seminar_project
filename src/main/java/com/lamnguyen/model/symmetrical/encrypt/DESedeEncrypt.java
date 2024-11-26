/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:14 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical.encrypt;

import com.lamnguyen.model.symmetrical.ISymmetrical;
import com.lamnguyen.utils.IVUtil;
import com.lamnguyen.utils.PaddingUtil;
import lombok.NoArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@NoArgsConstructor
public class DESedeEncrypt extends ASymmetricalEncrypt {
    public DESedeEncrypt(String mode, String padding) {
        super(mode, padding);
    }

    public DESedeEncrypt(String mode, String padding, IvParameterSpec iv) {
        super(mode, padding, iv);
    }

    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        cipher = ISymmetrical.getCipherInstance(Algorithms.DESede, mode, padding);
    }


    /**
     * Tạo đối tượng KeyGenerator
     *
     * @return KeyGenerator
     * @serialData Size key support: 112, 168
     */
    @Override
    protected KeyGenerator initKeyGenerator() throws NoSuchAlgorithmException {
        ivSpec = IVUtil.generateIV(8);
        return KeyGenerator.getInstance(Algorithms.DESede.name());
    }

    @Override
    public byte[] encrypt(String data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalBlockSizeException e) {
            return cipher.doFinal(PaddingUtil.addPadding(8, data.getBytes(StandardCharsets.UTF_8)));
        }
    }
}