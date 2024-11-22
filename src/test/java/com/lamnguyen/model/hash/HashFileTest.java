/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:41 AM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.hash;

import com.lamnguyen.config.HashAlgorithmConfig;
import com.lamnguyen.model.hash.impl.HashFileImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class HashFileTest {
    static String file;

    @BeforeAll
    public static void init() {
        file = "/home/lam-nguyen/Desktop/hinh.png";
    }

    @Test
    public void md5Test() throws NoSuchAlgorithmException, IOException {
        System.out.println(HashFileImpl.getInstance("MD5").hash(file));
    }

    @Test
    public void allTest() {
        HashAlgorithmConfig.getInstance().getHashs().forEach(it -> {
            try {
                System.out.println(HashFileImpl.getInstance(it).hash(file));
            } catch (NoSuchAlgorithmException | IOException e) {
                e.printStackTrace();
            }
        });
    }
}
