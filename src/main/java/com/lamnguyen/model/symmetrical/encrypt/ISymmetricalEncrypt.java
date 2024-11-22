/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:40 AM - 15/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical.encrypt;

import com.lamnguyen.model.symmetrical.ISymmetrical;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.IOException;

public interface ISymmetricalEncrypt extends ISymmetrical {
    /**
     * Tạo một khóa bí mật cho thuật toán mã hóa.
     *
     * @param size Kích thước của khóa cần tạo.
     * @return Khóa bí mật được tạo.
     */
    SecretKey generateKey(int size);

    /**
     * Mã hóa dữ liệu đã cho bằng khóa đã tải.
     *
     * @param data Dữ liệu cần mã hóa.
     * @return Dữ liệu đã mã hóa dưới dạng mảng byte.
     * @throws IllegalBlockSizeException Nếu kích thước khối không hợp lệ.
     * @throws BadPaddingException       Nếu padding không hợp lệ.
     */
    byte[] encrypt(String data) throws IllegalBlockSizeException, BadPaddingException;

    /**
     * Mã hóa dữ liệu đã cho và mã hóa kết quả bằng Base64.
     *
     * @param data Dữ liệu cần mã hóa.
     * @return Dữ liệu đã mã hóa dưới dạng chuỗi mã hóa Base64.
     * @throws IllegalBlockSizeException Nếu kích thước khối không hợp lệ.
     * @throws BadPaddingException       Nếu padding không hợp lệ.
     */
    String encryptStringBase64(String data) throws IllegalBlockSizeException, BadPaddingException;

    /**
     * Mã hóa nội dung của tệp nguồn và ghi dữ liệu đã mã hóa vào tệp đích.
     *
     * @param source Đường dẫn đến tệp nguồn cần mã hóa.
     * @param dest   Đường dẫn đến tệp đích để ghi dữ liệu đã mã hóa.
     * @param append Có thêm vào tệp đích nếu nó đã tồn tại hay không.
     * @throws IOException               Lỗi đọc file.
     * @throws IllegalBlockSizeException Lỗi
     * @throws BadPaddingException       Lỗi
     */
    void encryptFile(String source, String dest, boolean append) throws IOException, IllegalBlockSizeException, BadPaddingException;
}