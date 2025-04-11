/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:06 PM - 11/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.config;

import lombok.Getter;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HashAlgorithmConfig {
    @Getter
    private List<String> hashAlgForText, hashAlgForFile;
    private static HashAlgorithmConfig instance;
    private List<String> hashAlgNotForFile = new ArrayList<>() {{
        add("HARAKA-256");
        add("HARAKA-512");
    }};

    private HashAlgorithmConfig() {
        initAlgorithms();
        hashAlgForText.sort(String::compareTo);
        hashAlgForText.sort(String::compareTo);
    }

    public static HashAlgorithmConfig getInstance() {
        if (instance == null) instance = new HashAlgorithmConfig();
        return instance;
    }


    private void initAlgorithms() {
        hashAlgForText = new ArrayList<>(Security.getAlgorithms("MessageDigest"));
        hashAlgForFile = Security.getAlgorithms("MessageDigest").stream().filter(it -> !hashAlgNotForFile.contains(it)).toList();
        hashAlgForText.add("BCrypt");
    }
}
