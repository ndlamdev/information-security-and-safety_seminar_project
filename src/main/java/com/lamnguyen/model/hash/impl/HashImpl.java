/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:33 AM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.hash.impl;

import com.lamnguyen.model.hash.IHash;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashImpl implements IHash {
    private MessageDigest digest;

    private HashImpl(String alg) throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance(alg);
    }

    public static IHash getInstance(String alg) throws NoSuchAlgorithmException {
        return new HashImpl(alg);
    }

    @Override
    public String hashFile(String src) throws IOException {
        File file = new File(src);
        if (!file.exists()) return null;
        DigestInputStream stream = new DigestInputStream(new BufferedInputStream(new FileInputStream(file)), digest);
        byte[] buffer = new byte[1024 * 10];
        while (stream.read(buffer) != -1) {
        }
        var result = new BigInteger(1, stream.getMessageDigest().digest()).toString(16);
        stream.close();
        return result;
    }

    @Override
    public String hashText(String data) {
        return Base64.getEncoder().encodeToString(digest.digest(data.getBytes(StandardCharsets.UTF_8)));
    }
}
