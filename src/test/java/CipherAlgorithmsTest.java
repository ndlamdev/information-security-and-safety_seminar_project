/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:06 AM - 08/11/2024
 * User: lam-nguyen
 **/

package test.java;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class CipherAlgorithmsTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("RC2");
        keyGen.init(254); // Từ 40 đến 2048-bit, 128-bit là phổ biến
        SecretKey key = keyGen.generateKey();
        System.out.println(key);
    }
}
