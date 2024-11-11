/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:06 AM - 08/11/2024
 * User: lam-nguyen
 **/

package test.java;

import java.security.Security;
import java.util.Set;

public class CipherAlgorithmsTest {
    public static void main(String[] args) {
        // Lấy tất cả các thuật toán Cipher được hỗ trợ
        Set<String> cipherAlgorithms = Security.getAlgorithms("Cipher");

        // In danh sách các thuật toán Cipher
        System.out.println("Các thuật toán Cipher hỗ trợ:");
        for (String algorithm : cipherAlgorithms) {
            if (algorithm.equals("RSA/ECB/PKCS1Padding")) System.out.println("Have");
            System.out.println(algorithm);
        }
    }
}
