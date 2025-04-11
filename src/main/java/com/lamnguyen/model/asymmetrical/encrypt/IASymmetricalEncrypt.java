/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.asymmetrical.encrypt;

import com.lamnguyen.model.asymmetrical.AsymmetricalKey;
import com.lamnguyen.model.symmetrical.ISymmetrical;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;

public interface IASymmetricalEncrypt {

    /**
     * Load khóa công khai vào đối tượng encrypt
     *
     * @param key PublicKey khóa công khai sẽ load
     */
    void loadKey(PublicKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException;

    /**
     * Tạo ra một bộ khóa bất đối xứng từ kích thược khóa nhập vào
     * <ul>
     *    <li>khóa công khai</li>
     *    <li>khóa riêng tư</li>
     * </ul>
     *
     * @param size kích thước khóa muốn tạo
     * @return AsymmetricalKey khóa bất đối xứng được tạo
     */
    AsymmetricalKey generateKey(int size) throws NoSuchAlgorithmException;

    /**
     * Mã hóa chuổi ký tự thành mảng byte đã mã hóa
     *
     * @param data String chuổi ký tự muốn mã hóa
     * @return byte[] mã byte đã mã hóa
     */
    byte[] encrypt(String data) throws IllegalBlockSizeException, BadPaddingException;

    /**
     * Mã hóa mảng byte dữ liệu và encode nó thành base64
     *
     * @param data byte[] mảng byte dữ liệu muốn mã hóa
     * @return chuổi ký tự base64 được encode từ mãng byte đã mã hóa
     */
    String encryptToBase64(byte[] data) throws IllegalBlockSizeException, BadPaddingException;

    /**
     * Mã hóa chuổi ký tự và encode nó thành base64
     *
     * @param data String chuổi ký tự muốn mã hóa
     * @return chuổi ký tự base64 được encode từ chuổi ký tự đã mã hóa
     */
    String encryptStringToBase64(String data) throws IllegalBlockSizeException, BadPaddingException;

    /**
     * Mã hóa file dữ vào thuật toán đối xừng và mã hóa khóa của thuật toán đối xứng bằng thuật toán bất đối xứng
     *
     * @param  algorithm ISymmetrical.Algorithms enum thuật toán mã hóa đối xứng sẽ dùng để mã hóa nội dung file
     * @param mode String chuổi mode muốn dùng cho thuật toán đối xứng
     * @param padding String chuổi padding muốn dùng cho thuật toán đối xứng
     * */
    void encryptFile(ISymmetrical.Algorithms algorithm, String mode, String padding, String source, String dest) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException;
}