/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:32 AM - 05/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical;

import com.lamnguyen.model.symmetrical.decrypt.ISymmetricalDecrypt;
import com.lamnguyen.model.symmetrical.encrypt.ISymmetricalEncrypt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AESTest {
    static ISymmetricalEncrypt encrypt;
    static ISymmetricalDecrypt decrypt;
    static SymmetricalKey key;

    @BeforeAll
    static void init() throws Exception {
        encrypt = ISymmetrical.Factory.createEncrypt(ISymmetrical.Algorithms.AES, null, null, 128);
        key = new SymmetricalKey(encrypt.getKey(), encrypt.getIvSpec());
    }

    @Test
    void testForText() throws Exception {
        String data = "Nguyễn Đình Làm sinh ngày 13/05/2003!";
        String datEncrypt = encrypt.encryptStringBase64(data);
        String dataDecrypt = decrypt.decryptBase64ToString(datEncrypt);
        System.out.println(data);
        System.out.println(dataDecrypt);
        Assertions.assertEquals(data, dataDecrypt);
    }

    @Test
    void testForFile() throws Exception {
        String file = "/home/lam-nguyen/Desktop/hinh.png";
        String fileEncrypt = "/home/lam-nguyen/Desktop/hinh_encrypt.png";
        String fileDecrypt = "/home/lam-nguyen/Desktop/hinh_decrypt.png";
        encrypt.encryptFile(file, fileEncrypt, false);
        decrypt.decryptFile(fileEncrypt, fileDecrypt, 0);
    }

    @Test
    void allTest() throws Exception {
        SymmetricalTest.allTest(ISymmetrical.Algorithms.AES.name(), 128);
    }
}
