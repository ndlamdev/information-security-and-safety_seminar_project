/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:40 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.security.symmetrical.decrypt;

import com.lamnguyen.security.symmetrical.ASymmetrical;
import com.lamnguyen.security.symmetrical.SymmetricalKey;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public abstract class ASymmetricalDecrypt extends ASymmetrical implements ISymmetricalDecrypt {
    public ASymmetricalDecrypt(SymmetricalKey key, String mode, String padding) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        super(mode, padding);
        ivSpec = key.iv();
        loadKey(key.key());
    }

    public ASymmetricalDecrypt(SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        super(null, null);
        ivSpec = key.iv();
        loadKey(key.key());
    }

    @Override
    public final void loadKey(SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        super.loadKey(key);
        initCipher();
        if (ivSpec != null) cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        else cipher.init(Cipher.DECRYPT_MODE, key);
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

    /**
     * Giải mã nội dung của tệp nguồn và ghi dữ liệu đã mã hóa vào tệp đích.
     *
     * @param source Đường dẫn đến tệp nguồn cần mã hóa.
     * @param dest   Đường dẫn đến tệp đích để ghi dữ liệu đã mã hóa.
     * @param skip   Số byte sẽ skip để đến được phần nội dung.
     */
    @Override
    public final void decryptFile(String source, String dest, long skip) throws IOException, IllegalBlockSizeException, BadPaddingException {
        DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(source)));
        BufferedOutputStream bufferOutput = new BufferedOutputStream(new FileOutputStream(dest));
        CipherOutputStream output = new CipherOutputStream(bufferOutput, cipher);

        input.skip(skip);

        byte[] buffer = new byte[1024 * 10];
        int i;
        while ((i = input.read(buffer)) != -1) output.write(buffer, 0, i);
        input.close();

        byte[] finalBuffer = cipher.doFinal();
        if (finalBuffer != null) output.write(finalBuffer);
        output.close();
    }

    private void readIV(DataInputStream input) {
        try {
            String ivBase64 = input.readUTF();
            byte[] iv = Base64.getDecoder().decode(ivBase64);
            ivSpec = new IvParameterSpec(iv);
        } catch (IOException ignored) {
        }
    }
}