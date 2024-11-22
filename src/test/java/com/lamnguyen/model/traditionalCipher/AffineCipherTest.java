/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:47 AM - 02/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher;

import com.lamnguyen.model.traditionalCipher.algorithm.AffineCipher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AffineCipherTest")
public class AffineCipherTest {
    ITraditionalCipher affineCipherEncrypt;
    ITraditionalCipher affineCipherDecrypt;

    @Test
    public void algorithmTest() throws Exception {
        var key = new AffineCipher.AffineKey(13, 5);
        affineCipherEncrypt = new AffineCipher(key, ITraditionalCipher.SecureLanguage.VN);
        affineCipherEncrypt.init(ITraditionalCipher.SecureMode.ENCRYPT);
        affineCipherDecrypt = new AffineCipher(key, ITraditionalCipher.SecureLanguage.VN);
        affineCipherDecrypt.init(ITraditionalCipher.SecureMode.DECRYPT);

        var data = "Nguyễn Đình Lam là sinh viên trường Đại học Nông Lâm  sinh ngày 13 tháng 5 năm 2003!";
        var encrypted = affineCipherEncrypt.doFinal(data);
        var decrypted = affineCipherDecrypt.doFinal(encrypted);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
        Assertions.assertNotEquals(encrypted, decrypted);
        Assertions.assertEquals(data, decrypted);
    }

    @Test
    public void generateKeyTest() throws Exception {
        var data = "Nguyễn Đình Lam là sinh viên trường Đại học Nông Lâm sinh ngày 13 tháng 5 năm 2003!";
        TraditionalKey<AffineCipher.AffineKey> traditionalKey;
        affineCipherEncrypt = new AffineCipher(ITraditionalCipher.SecureLanguage.VN);

        var decrypted = data;

        var repeat = 1000;
        while (repeat-- > 0 && data.equals(decrypted)) {
            traditionalKey = (TraditionalKey<AffineCipher.AffineKey>) affineCipherEncrypt.generateKey("100");
            System.out.println(traditionalKey.contentKey());
            affineCipherEncrypt.loadKey(traditionalKey);
            System.out.println(traditionalKey.contentKey());
            affineCipherEncrypt.init(ITraditionalCipher.SecureMode.ENCRYPT);
            affineCipherDecrypt = new AffineCipher(traditionalKey.contentKey(), ITraditionalCipher.SecureLanguage.VN);
            affineCipherDecrypt.init(ITraditionalCipher.SecureMode.DECRYPT);
            var encrypted = affineCipherEncrypt.doFinal(data);
            decrypted = affineCipherDecrypt.doFinal(encrypted);
        }

        System.out.println("Repeat = " + repeat + " Decrypted: " + decrypted);
        Assertions.assertEquals(repeat, -1);
    }
}
