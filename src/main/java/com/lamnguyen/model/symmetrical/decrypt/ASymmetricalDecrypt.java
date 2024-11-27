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
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
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
    public void decryptFile(String source, String dest, long skip) throws IOException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException {
        var file = new File(source);
        DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));
        if (input.skip(skip) != skip) throw new IOException();
        byte[] buffer = new byte[10 * 1024];
        var bytesRead = 0;
        var total = file.length() - skip;
        while ((bytesRead = input.read(buffer)) != -1) {
            if (total <= bytesRead) {
                output.write(PaddingUtil.removePadding(cipher.doFinal(Arrays.copyOf(buffer, bytesRead))));
                break;
            }
            var update = cipher.update(buffer);
            output.write(update);
            total -= bytesRead;
        }
        input.close();
        output.close();
    }
}