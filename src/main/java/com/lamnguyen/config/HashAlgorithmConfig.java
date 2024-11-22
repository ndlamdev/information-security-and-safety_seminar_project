/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:06 PM - 11/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.config;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class HashAlgorithmConfig {
    @Getter
    private List<String> hashs;
    private static HashAlgorithmConfig instance;

    private HashAlgorithmConfig() {
        initAlgorithms();
    }

    public static HashAlgorithmConfig getInstance() {
        if (instance == null) instance = new HashAlgorithmConfig();
        return instance;
    }


    private void initAlgorithms() {
        hashs = new ArrayList<>(){{
            add("MD2");
            add("MD5");
            add("SHA-1");
            add("SHA-224");
            add("SHA-256");
            add("SHA-384");
            add("SHA-512");
            add("SHA-512/224");
            add("SHA-512/256");
            add("SHA3-224");
            add("SHA3-256");
            add("SHA3-384");
            add("SHA3-512");
        }};
    }
}
