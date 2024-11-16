/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:32 AM - 05/11/2024
 * User: lam-nguyen
 **/

package test.java.security.asymmertrical;

import main.java.security.asymmetrical.decrypt.IASymmetricalDecrypt;
import main.java.security.asymmetrical.decrypt.RSADecrypt;
import main.java.security.asymmetrical.encrypt.IASymmetricalEncrypt;
import main.java.security.asymmetrical.encrypt.RSAEncrypt;
import main.java.security.symmetrical.ISymmetrical;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class RSATest {
    static IASymmetricalEncrypt encrypt;
    static IASymmetricalDecrypt decrypt;
    static SecretKey aesKey;

    @BeforeAll
    static void init() throws Exception {
        encrypt = new RSAEncrypt();
        decrypt = new RSADecrypt();
        var key = encrypt.generateKey(1024);
        decrypt.loadKey(key.privateKey());
        encrypt.loadKey(key.publicKey());
    }

    @Test
    void testForText() throws Exception {
        String data = "Nguyễn Đình Làm sinh ngày 13/05/2003!";
        String datEncrypt = encrypt.encryptStringToBase64(data);
        String dataDecrypt = decrypt.decryptBase64ToString(datEncrypt);
        Assertions.assertEquals(data, dataDecrypt);
    }

    @Test
    void testForFile() throws Exception {
        String file = "/home/lam-nguyen/Desktop/hinh.png";
        String fileEncrypt = "/home/lam-nguyen/Desktop/hinh_encrypt.png";
        String fileDecrypt = "/home/lam-nguyen/Desktop/hinh_decrypt.png";
        encrypt.encryptFile(ISymmetrical.Algorithms.DES, 56, file, fileEncrypt);
        decrypt.decryptFile(fileEncrypt, fileDecrypt);
    }
}
