/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:47 AM - 02/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.security.traditionalCipher;

import com.lamnguyen.security.traditionalCipher.algorithm.ShiftCipher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ShiftCipherTest")
public class ShiftCipherTest {
    ITraditionalCipher shiftCipherEncrypt;
    ITraditionalCipher shiftCipherDecrypt;

    @Test
    public void algorithmTest() throws Exception {
        var key = 13;
        shiftCipherEncrypt = new ShiftCipher(key, ITraditionalCipher.SecureLanguage.VN);
        shiftCipherEncrypt.init(ITraditionalCipher.SecureMode.ENCRYPT);
        shiftCipherDecrypt = new ShiftCipher(key, ITraditionalCipher.SecureLanguage.VN);
        shiftCipherDecrypt.init(ITraditionalCipher.SecureMode.DECRYPT);

        var data = "Nguyễn Đình Lam là sinh viên trường Đại học Nông Lâm  sinh ngày 13 tháng 5 năm 2003!";
        var encrypted = shiftCipherEncrypt.doFinal(data);
        var decrypted = shiftCipherDecrypt.doFinal(encrypted);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
        Assertions.assertNotEquals(encrypted, decrypted);
        Assertions.assertEquals(data, decrypted);
    }

    @Test
    public void generateKeyTest() throws Exception {
        var data = "Nguyễn Đình Lam là sinh viên trường Đại học Nông Lâm sinh ngày 13 tháng 5 năm 2003!";
        TraditionalKey<Integer> traditionalKey;
        shiftCipherEncrypt = new ShiftCipher(ITraditionalCipher.SecureLanguage.VN);

        var decrypted = data;

        var repeat = 1000;
        while (repeat-- > 0 && data.equals(decrypted)) {
            traditionalKey = (TraditionalKey<Integer>) shiftCipherEncrypt.generateKey(3);
            shiftCipherEncrypt.loadKey(traditionalKey);
            shiftCipherEncrypt.init(ITraditionalCipher.SecureMode.ENCRYPT);
            shiftCipherDecrypt = new ShiftCipher(traditionalKey.contentKey(), ITraditionalCipher.SecureLanguage.VN);
            shiftCipherDecrypt.init(ITraditionalCipher.SecureMode.DECRYPT);
            var encrypted = shiftCipherEncrypt.doFinal(data);
            decrypted = shiftCipherDecrypt.doFinal(encrypted);
        }

        System.out.println("Repeat = " + repeat + " Decrypted: " + decrypted);
        Assertions.assertEquals(repeat, -1);
    }
}
