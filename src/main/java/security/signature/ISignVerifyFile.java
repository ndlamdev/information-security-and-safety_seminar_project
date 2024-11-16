/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:38 PM - 16/11/2024
 * User: lam-nguyen
 **/

package main.java.security.signature;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Signature;
import java.security.SignatureException;

public interface ISignVerifyFile {
    static void signVerifyHelper(Signature signature, String file) throws IOException, SignatureException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[1024 * 10];
        int bytesRead;
        while ((bytesRead = bis.read(buffer)) != -1) signature.update(buffer, 0, bytesRead);
        bis.close();
    }
}
