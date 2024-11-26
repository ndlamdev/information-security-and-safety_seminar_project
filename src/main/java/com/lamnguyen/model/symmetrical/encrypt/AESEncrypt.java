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
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

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

    @Override
    public void encryptFile(String source, String dest, boolean append) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        var file = new File(source);
        DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest, append)));
        byte[] buffer = new byte[10 * 1024];
        int bytesRead;
        var total = file.length();
        while ((bytesRead = input.read(buffer)) != -1) {
            if (total > bytesRead) {
                output.write(cipher.update(buffer));
                total -= bytesRead;
                continue;
            }

            try {
                output.write(cipher.doFinal(buffer, 0, bytesRead));
            } catch (IllegalBlockSizeException e) {
                try {
                    init(Cipher.ENCRYPT_MODE, true);
                } catch (InvalidAlgorithmParameterException ignored) {
                    init(Cipher.ENCRYPT_MODE, false);
                }
                output.write(cipher.doFinal(PaddingUtil.addPadding(16, Arrays.copyOf(buffer, bytesRead))));
            }
        }
        input.close();
        output.close();
    }
}