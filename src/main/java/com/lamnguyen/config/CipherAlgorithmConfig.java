/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:06 PM - 11/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.config;

import lombok.Getter;
import com.lamnguyen.model.asymmetrical.IAsymmetrical;
import com.lamnguyen.model.symmetrical.ISymmetrical;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CipherAlgorithmConfig {
    @Getter
    private List<Algorithm> algorithmSymmetrical, algorithmAsymmetrical;
    private static CipherAlgorithmConfig instance;

    public record Algorithm(String alg, List<String> modes, List<String> paddings) {
    }

    private CipherAlgorithmConfig() {
        initAlgorithms();
        algorithmSymmetrical.sort(Comparator.comparing(algorithm -> algorithm.alg));
        algorithmAsymmetrical.sort(Comparator.comparing(algorithm -> algorithm.alg));
    }

    public static CipherAlgorithmConfig getInstance() {
        if (instance == null) instance = new CipherAlgorithmConfig();
        return instance;
    }


    private void initAlgorithms() {
        var modes = new ArrayList<String>() {{
            add("");
            add("CBC");
            add("ECB");
            add("CFB");
        }};
        var paddings = new ArrayList<String>() {{
            add("");
            add("PKCS5Padding");
            add("PKCS7Padding");
            add("ISO10126Padding");
            add("X9.23Padding");
        }};
        algorithmSymmetrical = new ArrayList<>() {{
            add(new Algorithm(ISymmetrical.Algorithms.AES.name(), modes, new ArrayList<>(paddings) {{
                add("NoPadding");
            }}));
            add(new Algorithm(ISymmetrical.Algorithms.DES.name(), modes, new ArrayList<>(paddings) {{
                add("NoPadding");
            }}));
            add(new Algorithm(ISymmetrical.Algorithms.DESede.name(), modes, paddings));
            add(new Algorithm(ISymmetrical.Algorithms.Camellia.name(), modes, paddings));
            add(new Algorithm(ISymmetrical.Algorithms.Blowfish.name(), modes,paddings));
            add(new Algorithm(ISymmetrical.Algorithms.AESWrap.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.AESWrapPad.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.ARCFOUR.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.ChaCha20.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.ChaCha20Poly1305.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.DESedeWrap.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.RC2.name(), modes, paddings));
            add(new Algorithm(ISymmetrical.Algorithms.RC4.name(), modes, paddings));
            add(new Algorithm(ISymmetrical.Algorithms.RC5.name(), modes, paddings));
            add(new Algorithm(ISymmetrical.Algorithms.RC6.name(), modes, paddings));
        }};


        algorithmAsymmetrical = new ArrayList<>() {{
            add(new Algorithm(IAsymmetrical.Algorithms.RSA.name(), new ArrayList<>() {{
                add("ECB");
            }}, new ArrayList<>() {{
                add("PKCS1Padding");
                add("OAEPWithSHA-1AndMGF1Padding");
                add("OAEPWithSHA-256AndMGF1Padding");
            }}));

//            add(new Algorithm(IAsymmetrical.Algorithms.ECIES.name(), new ArrayList<>(), new ArrayList<>()));
        }};
    }
}
