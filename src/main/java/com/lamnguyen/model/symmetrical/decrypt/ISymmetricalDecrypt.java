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

public interface ISymmetricalDecrypt extends ISymmetrical {
    String decrypt(byte[] data);

    String decryptBase64ToString(String data);

    /**
     * Giải mã nội dung của tệp nguồn và ghi dữ liệu đã mã hóa vào tệp đích.
     *
     * @param source Đường dẫn đến tệp nguồn cần mã hóa.
     * @param dest   Đường dẫn đến tệp đích để ghi dữ liệu đã mã hóa.
     * @param skip   Số byte sẽ skip để đến được phần nội dung.
     * @throws IOException               Lỗi đọc file.
     * @throws IllegalBlockSizeException Lỗi
     * @throws BadPaddingException       Lỗi
     */
    void decryptFile(String source, String dest, long skip) throws IOException, IllegalBlockSizeException, BadPaddingException;
}