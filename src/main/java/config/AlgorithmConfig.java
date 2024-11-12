/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:06 PM - 11/11/2024
 * User: lam-nguyen
 **/

package main.java.config;

import lombok.Getter;
import main.java.security.symmetrical.ISymmetrical;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmConfig {
    @Getter
    private List<Algorithm> algorithmSymmetrical;
    private static AlgorithmConfig instance;

    public record Algorithm(String alg, List<String> modes, List<String> paddings) {


    }

    private AlgorithmConfig() {
        initAlgorithms();
    }

    public static AlgorithmConfig getInstance() {
        if (instance == null) instance = new AlgorithmConfig();
        return instance;
    }


    private void initAlgorithms() {
        algorithmSymmetrical = new ArrayList<>() {{
            add(new Algorithm(ISymmetrical.Algorithms.AES.name(), new ArrayList<>() {{
                add("");
                add("CBC");
                add("ECB");
            }}, new ArrayList<>() {{
                add("");
                add("NoPadding");
                add("PKCS5Padding");
            }}));
            add(new Algorithm(ISymmetrical.Algorithms.DES.name(), new ArrayList<>() {{
                add("");
                add("CBC");
                add("ECB");
            }}, new ArrayList<>() {{
                add("");
                add("NoPadding");
                add("PKCS5Padding");
            }}));
            add(new Algorithm(ISymmetrical.Algorithms.DESede.name(), new ArrayList<>() {{
                add("");
                add("CBC");
                add("ECB");
            }}, new ArrayList<>() {{
                add("");
                add("NoPadding");
                add("PKCS5Padding");
            }}));
            add(new Algorithm(ISymmetrical.Algorithms.DESede.name(), new ArrayList<>() {{
                add("");
                add("ECB");
            }}, new ArrayList<>() {{
                add("");
                add("PKCS1Padding");
                add("OAEPWithSHA-1AndMGF1Padding");
                add("OAEPWithSHA-256AndMGF1Padding");
            }}));

            add(new Algorithm(ISymmetrical.Algorithms.AESWrap.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.AESWrapPad.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.ARCFOUR.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.Blowfish.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.ChaCha20.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.ChaCha20Poly1305.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.DESedeWrap.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.RC2.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.RC4.name(), new ArrayList<>(), new ArrayList<>()));
            add(new Algorithm(ISymmetrical.Algorithms.RC4.name(), new ArrayList<>(), new ArrayList<>()));
        }};
    }
}
