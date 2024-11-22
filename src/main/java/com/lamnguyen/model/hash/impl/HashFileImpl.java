/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:33 AM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.hash.impl;

import com.lamnguyen.model.hash.IHashFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFileImpl implements IHashFile {
    private MessageDigest digest;

    private HashFileImpl(String alg) throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance(alg);
    }

    public static IHashFile getInstance(String alg) throws NoSuchAlgorithmException {
        return new HashFileImpl(alg);
    }

    @Override
    public String hash(String src) throws IOException {
        File file = new File(src);
        if (!file.exists()) return null;
        DigestInputStream stream = new DigestInputStream(new BufferedInputStream(new FileInputStream(file)), digest);
        byte[] buffer = new byte[1024 * 10];
        while (stream.read(buffer) != -1) {
        }

        return new BigInteger(1, stream.getMessageDigest().digest()).toString(16);
    }
}
