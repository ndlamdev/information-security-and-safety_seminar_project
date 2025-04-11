/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:14 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical.encrypt;

import com.lamnguyen.model.symmetrical.SymmetricalKey;
import com.lamnguyen.utils.PaddingUtil;
import lombok.NoArgsConstructor;
import com.lamnguyen.model.symmetrical.ISymmetrical;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

@NoArgsConstructor
public class AESWrapEncrypt extends ASymmetricalEncrypt {
    public AESWrapEncrypt(String mode, String padding) {
        super(mode, padding);
    }

    public AESWrapEncrypt(String mode, String padding, IvParameterSpec iv) {
        super(mode, padding, iv);
    }

    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        cipher = ISymmetrical.getCipherInstance(Algorithms.AESWrap, mode, padding);
    }

    /**
     * Tạo đối tượng KeyGenerator
     *
     * @return KeyGenerator
     * @serialData Size key support:  128, 192, 256
     */
    @Override
    protected KeyGenerator initKeyGenerator() throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance(Algorithms.AES.name());
    }

    @Override
    public void loadKey(SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
        this.key = key.key();
        this.ivSpec = key.iv();
        initCipher();
        init(Cipher.WRAP_MODE, false);
    }

    @Override
    public byte[] encrypt(String data) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        return cipher.wrap(new SecretKeySpec(PaddingUtil.addPadding(16, data.getBytes(StandardCharsets.UTF_8)), Algorithms.AES.name()));
    }

    @Override
    public void encryptFile(String source, String dest, boolean append) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(source)));
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest, append)));
        byte[] buffer = new byte[24 * 512];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1)
            output.write(encrypt(new String(Arrays.copyOf(buffer, bytesRead))));
        input.close();
        output.close();
    }
}