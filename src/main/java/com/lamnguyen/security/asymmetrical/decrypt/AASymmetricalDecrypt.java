/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.security.asymmetrical.decrypt;

import com.lamnguyen.security.asymmetrical.AAsymmetrical;
import com.lamnguyen.security.symmetrical.ISymmetrical;

import javax.crypto.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

public abstract class AASymmetricalDecrypt extends AAsymmetrical implements IASymmetricalDecrypt {
    protected PrivateKey key;

    public AASymmetricalDecrypt(PrivateKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.loadKey(key);
    }

    public AASymmetricalDecrypt() {
    }

    @Override
    public final void decryptFile(String source, String dest) throws HeaderException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, IOException, BadPaddingException {
        HeaderFileEncrypt header = loadHeader(source);
        if (header == null) throw new HeaderException("Không tồn tại header");

        ISymmetrical.Factory.createDecrypt(header.algorithm, null, null, null).decryptFile(source, dest, header.skip);
    }

    private HeaderFileEncrypt loadHeader(String source) {
        DataInputStream input;
        try {
            input = new DataInputStream(new FileInputStream(source));
        } catch (FileNotFoundException e) {
            return null;
        }
        try {
            String algorithm = input.readUTF();
            String base64Encode = input.readUTF();
            long skip = input.readLong();
            input.close();
            ISymmetrical.Algorithms alg;
            try {
                alg = ISymmetrical.Algorithms.valueOf(algorithm);
            } catch (Exception e) {
                return null;
            }
            String decodeKey = decryptBase64ToString(base64Encode);
            SecretKey key = ISymmetrical.generateKeyByEncodeKey(decodeKey, algorithm);
            if (key == null) {
                return null;
            }
            return new HeaderFileEncrypt(key, alg, skip);
        } catch (IOException | IllegalBlockSizeException | RuntimeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public final void loadKey(PrivateKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.key = key;
        initCipher();
        cipher.init(Cipher.DECRYPT_MODE, key);
    }

    private record HeaderFileEncrypt(SecretKey key, ISymmetrical.Algorithms algorithm, long skip) {
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