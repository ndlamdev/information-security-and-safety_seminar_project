/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:40 AM - 15/10/2024
 * User: lam-nguyen
 **/
package com.lamnguyen.model.symmetrical.decrypt;

import com.lamnguyen.model.symmetrical.ISymmetrical;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface ISymmetricalDecrypt extends ISymmetrical {
    /**
     * Giải mã mảng byte vào tạo đối tượng String để dịch mảng byte đã giải mã
     *
     * @param data mảng byte dữ liệu cần được giải mã
     * @return Chuổi String đã được giải mã
     */
    String decrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException;

    /**
     * Giải mã chuổi dữ liệu đã được encode bằng base64 vào tạo đối tượng String để dịch mảng byte đã giải mã
     *
     * @param data chuổi dữ liệu base64 đã được mã hóa.
     * @return Chuổi String đã được giải mã
     */
    String decryptBase64ToString(String data) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException;

    /**
     * Giải mã nội dung của tệp nguồn và ghi dữ liệu đã mã hóa vào tệp đích.
     *
     * @param source – Đường dẫn đến tệp nguồn cần mã hóa. dest – Đường dẫn đến tệp đích để ghi dữ liệu đã mã hóa. skip – Số byte sẽ skip để đến được phần nội dung.
     * @throws IOException               – Lỗi đọc file.
     * @throws IllegalBlockSizeException – Lỗi
     * @throws BadPaddingException       – Lỗi
     */
    void decryptFile(String source, String dest, long skip) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException;
}