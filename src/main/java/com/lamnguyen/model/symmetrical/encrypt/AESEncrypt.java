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

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.*;

@NoArgsConstructor
public class AESEncrypt extends ASymmetricalEncrypt {
    public AESEncrypt(String mode, String padding) {
        super(mode, padding);
    }

    public AESEncrypt(String mode, String padding, IvParameterSpec iv) {
        super(mode, padding, iv);
    }

    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException {
        cipher = ISymmetrical.getCipherInstance(Algorithms.AES, mode, padding);
    }

    /**
     * Tạo đối tượng KeyGenerator
     *
     * @return KeyGenerator
     * @serialData Size key support:  128, 192, 256
     */
    @Override
    protected KeyGenerator initKeyGenerator() throws NoSuchAlgorithmException {
        ivSpec = IVUtil.generateIV(16);
        return KeyGenerator.getInstance(Algorithms.AES.name());
    }

    @Override
    public byte[] encrypt(String data) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        try {
            return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalBlockSizeException e) {
            try {
                init(Cipher.ENCRYPT_MODE, true);
            } catch (InvalidAlgorithmParameterException ignored) {
                init(Cipher.ENCRYPT_MODE, false);
            }
            return cipher.doFinal(PaddingUtil.addPadding(16, data.getBytes(StandardCharsets.UTF_8)));
        }
    }
}