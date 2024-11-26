/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:14 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical.encrypt;

import com.lamnguyen.model.symmetrical.ISymmetrical;
import com.lamnguyen.model.symmetrical.SymmetricalKey;
import com.lamnguyen.utils.PaddingUtil;
import lombok.NoArgsConstructor;

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
public class DESedeWrapEncrypt extends ASymmetricalEncrypt {
    public DESedeWrapEncrypt(String mode, String padding) {
        super(mode, padding);
    }

    public DESedeWrapEncrypt(String mode, String padding, IvParameterSpec iv) {
        super(mode, padding, iv);
    }

    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        cipher = ISymmetrical.getCipherInstance(Algorithms.DESedeWrap, mode, padding);
    }

    /**
     * Tạo đối tượng KeyGenerator
     *
     * @return KeyGenerator
     * @serialData Size key support: 112, 168
     */
    @Override
    protected KeyGenerator initKeyGenerator() throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance(Algorithms.DESede.name());
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
        return cipher.wrap(new SecretKeySpec(PaddingUtil.addPadding(16, data.getBytes(StandardCharsets.UTF_8)), Algorithms.DES.name()));
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