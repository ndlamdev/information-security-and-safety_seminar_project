/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:32 AM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.hash;

import com.lamnguyen.model.hash.impl.BCryptHashPassword;
import com.lamnguyen.model.hash.impl.HashImpl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface IHash {
    String hashFile(String src) throws IOException;

    String hashText(String data);

    class Factory {
      public   static IHash getInstance(String alg) throws NoSuchAlgorithmException {
            return switch (alg) {
                case "BCrypt" -> BCryptHashPassword.getInstance();
                default -> HashImpl.getInstance(alg);
            };
        }
    }
}
