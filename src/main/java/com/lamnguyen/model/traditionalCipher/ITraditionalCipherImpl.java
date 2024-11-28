/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:27 AM - 28/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher;

public interface ITraditionalCipherImpl {

    /**
     * Lấy khóa của thuật toán mã hóa của điển.
     */
    TraditionalKey<?> getTraditionalKey();

    /**
     * Mã hóa mãng dữ liệu
     *
     * @param data byte[] mãng dữ liệu cần được mã hóa hoặc giải mã.
     * @return byte[] mãng dữ liệu đã được mã hóa hoặc giải mã
     */
    byte[] doFinal(byte[] data) throws Exception;

    /**
     * Mã hóa chuổi kỹ tự
     *
     * @return String chuổi dữ liệu đã được mã hóa hoặc giải mã
     */
    String doFinal(String data) throws Exception;
}

