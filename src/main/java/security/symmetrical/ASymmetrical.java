/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:49 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.security.symmetrical;

import lombok.Getter;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public abstract class ASymmetrical implements ISymmetrical {
    protected SecretKey key;
    protected Cipher cipher;
    protected String mode, padding;
    @Getter
    protected IvParameterSpec ivSpec;

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
    public void loadKey(SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        this.key = key;
    }

    /**
     * Lấy khóa bí mật của thuật toán.
     *
     * @return key Khóa bí mật của thuật toán.
     */
    @Override
    public SecretKey getKey() {
        return this.key;
    }

    /**
     * Khởi tạo đối tượng Cipher với thuật toán tương ứng.
     *
     * @throws NoSuchPaddingException   Lỗi khi thêm padding vào thuật toán
     * @throws NoSuchAlgorithmException Lỗi thuật toán không tồn tại
     */
    protected abstract void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException;


    protected String getExtension() {
        return (mode == null || mode.isBlank() ? "" : "/" + mode) + (padding == null || padding.isBlank() ? "" : "/" + padding);
    }
}