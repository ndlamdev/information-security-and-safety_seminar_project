/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:46 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical.encrypt;

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

public abstract class ASymmetricalEncrypt extends ASymmetrical implements ISymmetricalEncrypt {

    public ASymmetricalEncrypt(String mode, String padding) {
        super(mode, padding);
    }

    public ASymmetricalEncrypt(String mode, String padding, IvParameterSpec iv) {
        super(mode, padding, iv);
    }

    public ASymmetricalEncrypt() {
        super(null, null);
    }

    /**
     * Tải khóa bí mật và khởi tạo đối tượng Cipher để mã hóa dữ liệu.
     *
     * @param key Khóa bí mật cần tải.
     * @throws NoSuchPaddingException   Nếu thuật toán padding không tồn tại.
     * @throws NoSuchAlgorithmException Nếu thuật toán mã hóa không tồn tại.
     * @throws InvalidKeyException      Nếu khóa không hợp lệ.
     */
    @Override
    public void loadKey(SymmetricalKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException {
        super.loadKey(key);
        initCipher();
        try {
            init(Cipher.ENCRYPT_MODE, true);
        } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException ignored) {
            init(Cipher.ENCRYPT_MODE, false);
        }
    }

    /**
     * Mã hóa dữ liệu đã cho bằng khóa đã tải.
     *
     * @param data Dữ liệu cần mã hóa.
     * @return Dữ liệu đã mã hóa dưới dạng mảng byte.
     * @throws IllegalBlockSizeException Nếu kích thước khối không hợp lệ.
     * @throws BadPaddingException       Nếu padding không hợp lệ.
     */
    @Override
    public byte[] encrypt(String data) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Mã hóa dữ liệu đã cho và mã hóa kết quả bằng Base64.
     *
     * @param data Dữ liệu cần mã hóa.
     * @return Dữ liệu đã mã hóa dưới dạng chuỗi mã hóa Base64.
     * @throws IllegalBlockSizeException Nếu kích thước khối không hợp lệ.
     * @throws BadPaddingException       Nếu padding không hợp lệ.
     */
    @Override
    public final String encryptStringBase64(String data) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        return Base64.getEncoder().encodeToString(encrypt(data));
    }

    /**
     * Mã hóa nội dung của tệp nguồn và ghi dữ liệu đã mã hóa vào tệp đích.
     *
     * @param source Đường dẫn đến tệp nguồn cần mã hóa.
     * @param dest   Đường dẫn đến tệp đích để ghi dữ liệu đã mã hóa.
     * @param append Có thêm vào tệp đích nếu nó đã tồn tại hay không.
     */
    @Override
    public final void encryptFile(String source, String dest, boolean append) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(source)));
        BufferedOutputStream bufferOutput = new BufferedOutputStream(new FileOutputStream(dest, append));
        CipherOutputStream output = new CipherOutputStream(bufferOutput, cipher);

        byte[] buffer = new byte[1024 * 10];
        int i;

        try {
            while ((i = input.read(buffer)) != -1) output.write(buffer, 0, i);
        } catch (Exception e) {
            init(Cipher.ENCRYPT_MODE, true);
            while ((i = input.read(buffer)) != -1) output.write(buffer, 0, i);
        } finally {
            input.close();
        }

        buffer = cipher.doFinal();
        if (buffer != null) output.write(buffer);
        output.close();
    }

    /**
     * Khởi tạo đối tượng KeyGenerator cho thuật toán mã hóa.
     *
     * @return Đối tượng KeyGenerator đã được khởi tạo.
     * @throws NoSuchAlgorithmException Nếu thuật toán mã hóa không tồn tại.
     */
    protected abstract KeyGenerator initKeyGenerator() throws NoSuchAlgorithmException;

    /**
     * Tạo một khóa bí mật cho thuật toán mã hóa.
     *
     * @param size Kích thước của khóa cần tạo.
     * @return Khóa bí mật được tạo.
     */
    @Override
    public final SecretKey generateKey(int size) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = initKeyGenerator();
        keyGen.init(size);
        return keyGen.generateKey();
    }
}