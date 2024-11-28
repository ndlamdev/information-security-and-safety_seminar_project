/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.asymmetrical.decrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;

public interface IASymmetricalDecrypt {
    /**
     * Load khóa riêng tư vào đối tượng decrypt
     *
     * @param key PrivateKey khóa  riêng tư sẽ load
     */
    void loadKey(PrivateKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException;


    /**
     * Giải mã mảng byte dữ liệu đã mã hóa
     *
     * @param data byte[] mãng dữ liệu đã mã hóa
     * @return byte[] mãng dữ liệu đã được giải mã
     */
    byte[] decrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException;

    /**
     * Giải mã mãng byte dữ liệu đã mã hóa thành chuổi ký tự theo bảng mã UTF-8
     *
     * @param data byte[] mãng dữ liệu đã được mã hóa
     * @return Chuổi ký tự đã được giải mã và chuyển thành chuổi ký tự theo bảng mã UTF-8
     */
    String decryptToString(byte[] data) throws IllegalBlockSizeException, BadPaddingException;

    /**
     * Giải mã chuổi ký tự đã mã hóa và encode thành chuổi base64
     *
     * @param data String chuổi ký tự đã được mã hóa và encode thành base64
     * @return String Chuổi ký tự đã được giải mã
     */
    String decryptBase64ToString(String data) throws IllegalBlockSizeException, BadPaddingException;

    /**
     * Giải mã file đã được mã hóa bằng việc kết hợp thuật toán bất đối xứng và đối xứng.
     *
     * @param source String đường dẫn đến file cần giải mã
     * @param dest   String đường dẫn file đích đã được giải mã
     */
    void decryptFile(String source, String dest) throws AASymmetricalDecrypt.HeaderException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, IOException, BadPaddingException, NoSuchProviderException;

    /**
     * Lớp để đẩy lỗi khi file không có header hợp lệ so với thuật toán đang dung
     */
    class HeaderException extends Exception {

        public HeaderException(String message) {
            super(message);
        }
    }
}