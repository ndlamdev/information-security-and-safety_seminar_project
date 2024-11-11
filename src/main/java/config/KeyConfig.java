/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:11 AM - 11/11/2024
 * User: lam-nguyen
 **/

package main.java.config;

import lombok.Getter;
import main.java.security.symmetrical.ISymmetrical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyConfig {
    @Getter
    private Map<String, List<String>> mapAlgorithmSymmetrical, mapAlgorithmAsymmetrical;
    private static KeyConfig instance;

    public static KeyConfig getInstance() {
        if (instance == null) instance = new KeyConfig();
        return instance;
    }

    private KeyConfig() {
        this.initMapAlgorithmSymmetrical();
        this.initMapAlgorithmAsymmetrical();
    }

    private void initMapAlgorithmSymmetrical() {
        mapAlgorithmSymmetrical = new HashMap<>() {{
            put(ISymmetrical.Algorithms.DES.name(), new ArrayList<>() {{
                add("56");
            }});
            put(ISymmetrical.Algorithms.AES.name(), new ArrayList<>() {{
                add("128");
                add("192");
                add("256");
            }});
        }};
    }

    private void initMapAlgorithmAsymmetrical() {
        mapAlgorithmAsymmetrical = new HashMap<>() {{
            put(ISymmetrical.Algorithms.DES.name(), new ArrayList<>() {{
                add("56");
            }});
            put(ISymmetrical.Algorithms.AES.name(), new ArrayList<>() {{
                add("128");
                add("192");
                add("256");
            }});
        }};
    }
}
