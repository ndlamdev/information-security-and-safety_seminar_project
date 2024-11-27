/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.asymmetrical.decrypt;

import com.lamnguyen.model.asymmetrical.AAsymmetrical;
import com.lamnguyen.model.symmetrical.ISymmetrical;
import com.lamnguyen.model.symmetrical.SymmetricalKey;

import javax.crypto.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public abstract class AASymmetricalDecrypt extends AAsymmetrical implements IASymmetricalDecrypt {
    protected PrivateKey key;

    public AASymmetricalDecrypt(PrivateKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        this.loadKey(key);
    }

    public AASymmetricalDecrypt() {
    }

    @Override
    public final void decryptFile(String source, String dest) throws HeaderException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, IOException, BadPaddingException, NoSuchProviderException {
        HeaderFileEncrypt header = loadHeader(source);
        if (header == null) throw new HeaderException("Không tồn tại header");

        ISymmetrical.Factory.createDecrypt(header.algorithm, header.mode(), header.padding, header.key).decryptFile(source, dest, header.skip);
    }

    private HeaderFileEncrypt loadHeader(String source) {
        DataInputStream input;
        try {
            input = new DataInputStream(new FileInputStream(source));
        } catch (FileNotFoundException e) {
            return null;
        }

        try {
            var result = ISymmetrical.KeyFactory.readKey(input);
            long skip = input.readLong();
            var arr = result.key().getAlgorithm().split("/");
            HeaderFileEncrypt re;
            if (arr.length == 1) re = new HeaderFileEncrypt(result, arr[0], null, null, skip);
            else re = new HeaderFileEncrypt(result, arr[0], arr[1], arr[2], skip);
            input.close();
            return re;
        } catch (IOException e) {
            return null;
        }
    }


    @Override
    public final void loadKey(PrivateKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        this.key = key;
        initCipher();
        cipher.init(Cipher.DECRYPT_MODE, key);
    }

    private record HeaderFileEncrypt(SymmetricalKey key, String algorithm, String mode, String padding, long skip) {
    }

    @Override
    public final byte[] decrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException {
        return cipher.doFinal(data);
    }

    @Override
    public final String decryptToString(byte[] data) throws IllegalBlockSizeException, BadPaddingException {
        return new String(decrypt(data), StandardCharsets.UTF_8);
    }

    @Override
    public final String decryptBase64ToString(String base64) throws IllegalBlockSizeException, BadPaddingException {
        return decryptToString(Base64.getDecoder().decode(base64));
    }
}