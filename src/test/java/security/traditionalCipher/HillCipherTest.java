/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:47 AM - 02/11/2024
 * User: lam-nguyen
 **/

package test.java.security.traditionalCipher;

import main.java.helper.CharSetConfig;
import main.java.security.traditionalCipher.ITraditionalCipher;
import main.java.security.traditionalCipher.TraditionalKey;
import main.java.security.traditionalCipher.algorithm.HillCipher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HillCipherTest")
public class HillCipherTest {
    ITraditionalCipher hillCipherEncrypt;
    ITraditionalCipher hillCipherDecrypt;

    @Test
    public void algorithmTest() throws Exception {
        var data = "Nguyễn Đình Lam là sinh viên trường Đại học Nông Lâm sinh ngày 13 tháng 5 năm 2003!";
        var key = new int[][]{{59, 174}, {313, 91}};
        algorithmWIthKeyTest(data, key);
    }

    public void algorithmWIthKeyTest(String data, int[][] key) throws Exception {
        hillCipherEncrypt = new HillCipher(ITraditionalCipher.SecureLanguage.VN);
        hillCipherEncrypt.loadKey(new TraditionalKey<>(key));
        hillCipherEncrypt.init(ITraditionalCipher.SecureMode.ENCRYPT);
        hillCipherDecrypt = new HillCipher(key, ITraditionalCipher.SecureLanguage.VN);
        hillCipherDecrypt.init(ITraditionalCipher.SecureMode.DECRYPT);

        var encrypted = hillCipherEncrypt.doFinal(data);
        System.out.println("Encrypted: " + encrypted);
        var decrypted = hillCipherDecrypt.doFinal(encrypted);
        System.out.println("Decrypted: " + decrypted);
        Assertions.assertNotEquals(encrypted, decrypted);
        Assertions.assertEquals(data, decrypted);
    }

    @Test
    public void generateKeyTest() throws Exception {
        var data = "Nguyễn Đình Lam là sinh viên trường Đại học Nông Lâm sinh ngày 13 tháng 5 năm 2003!";
        TraditionalKey<int[][]> traditionalKey;
        hillCipherEncrypt = new HillCipher(ITraditionalCipher.SecureLanguage.VN);

        var decrypted = data;

        var repeat = 1000;
        while (repeat-- > 0 && data.equals(decrypted)) {
            traditionalKey = (TraditionalKey<int[][]>) hillCipherEncrypt.generateKey(2);
            hillCipherEncrypt.loadKey(traditionalKey);
            hillCipherEncrypt.init(ITraditionalCipher.SecureMode.ENCRYPT);
            hillCipherDecrypt = new HillCipher(traditionalKey.contentKey(), ITraditionalCipher.SecureLanguage.VN);
            hillCipherDecrypt.init(ITraditionalCipher.SecureMode.DECRYPT);
            var encrypted = hillCipherEncrypt.doFinal(data);
            decrypted = hillCipherDecrypt.doFinal(encrypted);
        }

        System.out.println("Repeat = " + repeat + " Decrypted: " + decrypted);
        Assertions.assertEquals(repeat, -1);
    }

    @Test
    public void generateKey2Test() throws Exception {
        var data = "DHNONGLAM";
        System.out.println(CharSetConfig.getMapChar(ITraditionalCipher.SecureLanguage.EN));
        TraditionalKey<int[][]> traditionalKey;
        hillCipherEncrypt = new HillCipher(ITraditionalCipher.SecureLanguage.EN);

        var decrypted = data;

        var repeat = 1000;
        while (repeat-- > 0 && data.equals(decrypted)) {
            traditionalKey = (TraditionalKey<int[][]>) hillCipherEncrypt.generateKey(3);
            hillCipherEncrypt.loadKey(traditionalKey);
            hillCipherEncrypt.init(ITraditionalCipher.SecureMode.ENCRYPT);
            hillCipherDecrypt = new HillCipher(traditionalKey.contentKey(), ITraditionalCipher.SecureLanguage.EN);
            hillCipherDecrypt.init(ITraditionalCipher.SecureMode.DECRYPT);
            var encrypted = hillCipherEncrypt.doFinal(data);
            decrypted = hillCipherDecrypt.doFinal(encrypted);
        }

        System.out.println("Repeat = " + repeat + " Decrypted: " + decrypted);
        Assertions.assertEquals(repeat, -1);
    }
}
