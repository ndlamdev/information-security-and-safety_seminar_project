/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:49 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical;

import lombok.Getter;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.AlgorithmParameterSpec;

public abstract class ASymmetrical implements ISymmetrical {
    @Getter
    protected SecretKey key;
    protected Cipher cipher;
    protected String mode, padding;
    @Getter
    protected AlgorithmParameterSpec ivSpec;

    public ASymmetrical(String mode, String padding) {
        this.mode = mode;
        this.padding = padding;
    }

    public ASymmetrical(String mode, String padding, IvParameterSpec ivSpec) {
        this.mode = mode;
        this.padding = padding;
        this.ivSpec = ivSpec;
    }

    /**
     * Tải khóa bí mật vào cipher.
     *
     * @param key Khóa bí mật cần tải
     * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán mã hóa
     * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại
     * @throws InvalidKeyException      Lỗi key không phù hợp với thuật toán
     */
    @Override
    public void loadKey(SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
        this.key = key.key();
        this.ivSpec = key.iv();
    }

    /**
     * Khởi tạo đối tượng Cipher với thuật toán tương ứng.
     *
     * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán
     * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại
     */
    protected abstract void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException;

    protected void init(int mode, boolean hasIv) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException {
        if (!hasIv) {
            cipher.init(mode, key);
            return;
        }
        if (ivSpec == null) throw new IllegalBlockSizeException();
        cipher.init(mode, key, ivSpec);
    }
}