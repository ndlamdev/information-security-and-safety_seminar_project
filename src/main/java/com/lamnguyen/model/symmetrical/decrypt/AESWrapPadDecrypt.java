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
import com.lamnguyen.utils.PaddingUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

public class AESWrapPadDecrypt extends ASymmetricalDecrypt {
    public AESWrapPadDecrypt(SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
        super(key);
    }

    public AESWrapPadDecrypt(SymmetricalKey key, String mode, String padding) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
        super(key, mode, padding);
    }


    @Override
    protected void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        cipher = ISymmetrical.getCipherInstance(Algorithms.AESWrapPad, mode, padding);
    }

    @Override
    public void loadKey(SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
        this.key = key.key();
        this.ivSpec = key.iv();
        initCipher();
        init(Cipher.UNWRAP_MODE, false);
    }

    @Override
    public final String decrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException {
        return new String(PaddingUtil.removePadding(cipher.unwrap(data, Algorithms.AES.name(), Cipher.SECRET_KEY).getEncoded()));
    }

    @Override
    public void decryptFile(String source, String dest, long skip) throws IOException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException {
        DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(source)));
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));
        if (input.skip(skip) != skip) throw new IOException();
        byte[] buffer = new byte[24 * 512];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1)
            output.write(decrypt(Arrays.copyOf(buffer, bytesRead)).getBytes(StandardCharsets.UTF_8));
        input.close();
        output.close();
    }
}