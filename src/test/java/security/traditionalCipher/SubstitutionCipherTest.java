/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:46 AM - 03/11/2024
 * User: lam-nguyen
 **/

package test.java.security.traditionalCipher;

import main.java.helper.CharSetConfig;
import main.java.security.traditionalCipher.ITraditionalCipher;
import main.java.security.traditionalCipher.TraditionalKey;
import main.java.security.traditionalCipher.algorithm.SubstitutionCipher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SubstitutionCipherTest {
    ITraditionalCipher substitutionCipherEncrypt;
    ITraditionalCipher substitutionCipherDecrypt;


    @Test
    public void algorithmTest() throws Exception {
        var listKey = CharSetConfig.getMapChar(ITraditionalCipher.SecureLanguage.VN).keySet().stream().toList();
        var listValue = CharSetConfig.getMapChar(ITraditionalCipher.SecureLanguage.VN, 13).entrySet().stream().toList();
        Map<Character, Character> key = new HashMap<>();
        for (var index = 0; index < listKey.size(); index++) {
            var k = listKey.get(index);
            var v = listValue.get(listValue.get(index).getValue() % listKey.size()).getKey();
            key.put(k, v);
        }
        System.out.println(key);
        substitutionCipherEncrypt = new SubstitutionCipher(key, ITraditionalCipher.SecureLanguage.VN);
        substitutionCipherEncrypt.init(ITraditionalCipher.SecureMode.ENCRYPT);
        substitutionCipherDecrypt = new SubstitutionCipher(key, ITraditionalCipher.SecureLanguage.VN);
        substitutionCipherDecrypt.init(ITraditionalCipher.SecureMode.DECRYPT);

        var data = "Nguyễn Đình Lam là sinh viên trường Đại học Nông Lâm  sinh ngày 13 tháng 5 năm 2003!";
        var encrypted = substitutionCipherEncrypt.doFinal(data);
        var decrypted = substitutionCipherDecrypt.doFinal(encrypted);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
        Assertions.assertNotEquals(encrypted, decrypted);
        Assertions.assertEquals(data, decrypted);
    }

    @Test
    public void generateKeyTest() throws Exception {
        var data = "Nguyễn Đình Lam là sinh viên trường Đại học Nông Lâm sinh ngày 13 tháng 5 năm 2003!";
        TraditionalKey<Map<Character, Character>> traditionalKey;
        substitutionCipherEncrypt = new SubstitutionCipher(ITraditionalCipher.SecureLanguage.VN);

        var decrypted = data;

        var repeat = 1000;
        while (repeat-- > 0 && data.equals(decrypted)) {
            traditionalKey = (TraditionalKey<Map<Character, Character>>) substitutionCipherEncrypt.generateKey(3);
            substitutionCipherEncrypt.loadKey(traditionalKey);
            substitutionCipherEncrypt.init(ITraditionalCipher.SecureMode.ENCRYPT);
            substitutionCipherDecrypt = new SubstitutionCipher(traditionalKey.contentKey(), ITraditionalCipher.SecureLanguage.VN);
            substitutionCipherDecrypt.init(ITraditionalCipher.SecureMode.DECRYPT);
            var encrypted = substitutionCipherEncrypt.doFinal(data);
            decrypted = substitutionCipherDecrypt.doFinal(encrypted);
            Assertions.assertNotEquals(encrypted, decrypted);
        }

        System.out.println("Repeat = " + repeat + " Decrypted: " + decrypted);
        Assertions.assertEquals(repeat, -1);
    }
}
