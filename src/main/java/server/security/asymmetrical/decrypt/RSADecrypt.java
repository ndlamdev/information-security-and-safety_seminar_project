/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:34 AM - 29/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.asymmetrical.decrypt;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Base64;

public class RSADecrypt extends AASymmetricalDecrypt {
    private PrivateKey key;
    private Cipher cipher;

    public RSADecrypt() {
    }

    @Override
    public void loadKey(PrivateKey key) throws Exception {
        this.key = key;
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
    }

    @Override
    public byte[] decrypt(byte[] data) throws Exception {
        return cipher.doFinal(data);
    }

    @Override
    public String decryptToString(byte[] data) throws Exception {
        return new String(decrypt(data), StandardCharsets.UTF_8);
    }

    @Override
    public String decryptBase64ToString(String base64) throws Exception {
        return decryptToString(Base64.getDecoder().decode(base64));
    }
}