/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:57 PM - 27/11/2024
 * User: kimin
 **/

package com.lamnguyen.model.hash.impl;

import com.lamnguyen.model.hash.IHash;
import org.bouncycastle.crypto.generators.SCrypt;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class BCryptHashPassword implements IHash {
    private static BCryptHashPassword instance;

    private BCryptHashPassword() {
    }

    public static BCryptHashPassword getInstance() {
        if (instance == null) instance = new BCryptHashPassword();
        return instance;
    }

    @Override
    public String hashFile(String src) throws IOException {
        throw new IOException("Dont support hash file");
    }

    @Override
    public String hashText(String data) {
        return BCrypt.hashpw(data, BCrypt.gensalt());
    }
}
