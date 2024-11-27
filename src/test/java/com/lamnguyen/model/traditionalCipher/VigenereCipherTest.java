/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:47 AM - 02/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher;

import com.lamnguyen.model.traditionalCipher.algorithm.VigenereCipher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("VigenereCipherTest")
public class VigenereCipherTest {
    ITraditionalCipher vigenereCipherEncrypt;
    ITraditionalCipher vigenereCipherDecrypt;


    @Test
    public void algorithmTest() throws Exception {
        var key = "CIPHER";
        vigenereCipherEncrypt = new VigenereCipher(key, ITraditionalCipher.SecureLanguage.VN);
        vigenereCipherEncrypt.init(ITraditionalCipher.SecureMode.ENCRYPT);
        vigenereCipherDecrypt = new VigenereCipher(key, ITraditionalCipher.SecureLanguage.VN);
        vigenereCipherDecrypt.init(ITraditionalCipher.SecureMode.DECRYPT);

        var data = "Nguyễn Đình Lam là sinh viên trường Đại học Nông Lâm  sinh ngày 13 tháng 5 năm 2003!";
        var encrypted = vigenereCipherEncrypt.doFinal(data);
        var decrypted = vigenereCipherDecrypt.doFinal(encrypted);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
        Assertions.assertNotEquals(encrypted, decrypted);
        Assertions.assertEquals(data, decrypted);
    }


    @Test
    public void generateKeyTest() throws Exception {
        var data = "Nguyễn Đình Lam là sinh viên trường Đại học Nông Lâm sinh ngày 13 tháng 5 năm 2003!";
        TraditionalKey<String> traditionalKey;
        vigenereCipherEncrypt = new VigenereCipher(ITraditionalCipher.SecureLanguage.VN);

        var decrypted = data;

        var repeat = 1000;
        while (repeat-- > 0 && data.equals(decrypted)) {
            traditionalKey = (TraditionalKey<String>) vigenereCipherEncrypt.generateKey("3");
            vigenereCipherEncrypt.loadTraditionalKey(traditionalKey);
            vigenereCipherEncrypt.init(ITraditionalCipher.SecureMode.ENCRYPT);
            vigenereCipherDecrypt = new VigenereCipher(traditionalKey.contentKey(), ITraditionalCipher.SecureLanguage.VN);
            vigenereCipherDecrypt.init(ITraditionalCipher.SecureMode.DECRYPT);
            var encrypted = vigenereCipherEncrypt.doFinal(data);
            decrypted = vigenereCipherDecrypt.doFinal(encrypted);
        }

        System.out.println("Repeat = " + repeat + " Decrypted: " + decrypted);
        Assertions.assertEquals(repeat, -1);
    }
}
