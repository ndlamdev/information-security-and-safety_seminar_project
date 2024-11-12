/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:40 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.security.symmetrical.decrypt;

import main.java.security.symmetrical.ASymmetrical;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public abstract class ASymmetricalDecrypt extends ASymmetrical implements ISymmetricalDecrypt {
    public ASymmetricalDecrypt(SecretKey key, String mode, String padding) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        super(mode, padding);
        loadKey(key);
    }

    public ASymmetricalDecrypt(SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        super(null, null);
        loadKey(key);
    }

    @Override
    public final void loadKey(SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        super.loadKey(key);
        initCipher();
        cipher.init(Cipher.DECRYPT_MODE, key);
    }

    @Override
    public final String decrypt(byte[] data) {
        try {
            return new String(cipher.doFinal(data));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public final String decryptBase64ToString(String data) {
        return decrypt(Base64.getDecoder().decode(data));
    }

    @Override
    public final boolean decryptFile(String source, String dest, long skip) throws IOException, IllegalBlockSizeException, BadPaddingException {
        BufferedInputStream bufferInput = new BufferedInputStream(new FileInputStream(source));
        CipherInputStream input = new CipherInputStream(bufferInput, cipher);
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(dest));

        bufferInput.skip(skip);
        byte[] buffer = new byte[1024 * 10];
        int i;
        while ((i = input.read(buffer)) != -1)
            output.write(buffer, 0, i);
        input.close();

        byte[] finalBuffer = cipher.doFinal();
        if (finalBuffer != null)
            output.write(finalBuffer);
        output.close();
        return true;
    }
}