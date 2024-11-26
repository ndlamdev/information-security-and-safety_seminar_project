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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

@NoArgsConstructor
public class DESEncrypt extends ASymmetricalEncrypt {
    public DESEncrypt(String mode, String padding) {
        super(mode, padding);
    }

    public DESEncrypt(String mode, String padding, IvParameterSpec iv) {
        super(mode, padding, iv);
    }

    @Override
    protected KeyGenerator initKeyGenerator() throws NoSuchAlgorithmException {
        ivSpec = IVUtil.generateIV(8);
        return KeyGenerator.getInstance(Algorithms.DES.name());
    }

    /**
     * Tạo đối tượng KeyGenerator
     *
     * @return KeyGenerator
     * @serialData Size key support: 56
     */
    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        cipher = ISymmetrical.getCipherInstance(Algorithms.DES, mode, padding);
    }

    @Override
    public byte[] encrypt(String data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalBlockSizeException e) {
            return cipher.doFinal(PaddingUtil.addPadding(8, data.getBytes(StandardCharsets.UTF_8)));
        }
    }

    @Override
    public void encryptFile(String source, String dest, boolean append) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        var file = new File(source);
        DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest, append)));
        byte[] buffer = new byte[1024 * 10];
        int bytesRead;
        var total = file.length();
        while ((bytesRead = input.read(buffer)) != -1) {
            if (total > bytesRead) {
                output.write(cipher.update(buffer));
                continue;
            }

            try {
                output.write(cipher.doFinal(buffer, 0, bytesRead));
            } catch (IllegalBlockSizeException e) {
                output.write(cipher.doFinal(PaddingUtil.addPadding(8, Arrays.copyOf(buffer, bytesRead))));
            }
        }
        input.close();
        output.close();
    }
}