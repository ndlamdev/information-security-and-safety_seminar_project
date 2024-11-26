/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:40 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical.decrypt;

import com.lamnguyen.model.symmetrical.ASymmetrical;
import com.lamnguyen.model.symmetrical.SymmetricalKey;
import com.lamnguyen.utils.PaddingUtil;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

public abstract class ASymmetricalDecrypt extends ASymmetrical implements ISymmetricalDecrypt {
    public ASymmetricalDecrypt(SymmetricalKey key, String mode, String padding) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
        super(mode, padding);
        loadKey(key);
    }

    public ASymmetricalDecrypt(SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
        super(null, null);
        loadKey(key);
    }

    @Override
    public void loadKey(SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
        super.loadKey(key);
        initCipher();
        try {
            init(Cipher.DECRYPT_MODE, false);
        } catch (InvalidKeyException e) {
            init(Cipher.DECRYPT_MODE, true);
        }
    }

    @Override
    public String decrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException {
        return new String(PaddingUtil.removePadding(cipher.doFinal(data)));
    }

    @Override
    public final String decryptBase64ToString(String data) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException {
        return decrypt(Base64.getDecoder().decode(data));
    }

    /**
     * Giải mã nội dung của tệp nguồn và ghi dữ liệu đã mã hóa vào tệp đích.
     *
     * @param source Đường dẫn đến tệp nguồn cần mã hóa.
     * @param dest   Đường dẫn đến tệp đích để ghi dữ liệu đã mã hóa.
     * @param skip   Số byte sẽ skip để đến được phần nội dung.
     */
    @Override
    public final void decryptFile(String source, String dest, long skip) throws IOException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException {
        BufferedInputStream bufferInput = new BufferedInputStream(new FileInputStream(source));
        CipherInputStream input = new CipherInputStream(bufferInput, cipher);
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));
        if (input.skip(skip) != skip) throw new IOException();
        byte[] buffer = new byte[1024 * 10];
        int i;

        try {
            while ((i = input.read(buffer)) != -1) output.write(buffer, 0, i);
        } catch (Exception e) {
            input.close();
            output.close();

            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            input = new CipherInputStream(bufferInput, cipher);
            output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));

            while ((i = input.read(buffer)) != -1) output.write(buffer, 0, i);
        } finally {
            input.close();
        }

        buffer = cipher.doFinal();
        if (buffer != null) output.write(buffer);
        output.close();
    }
}