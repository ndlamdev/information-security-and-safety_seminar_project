/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:11 AM - 11/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.config;

import lombok.Getter;
import com.lamnguyen.model.asymmetrical.IAsymmetrical;
import com.lamnguyen.model.symmetrical.ISymmetrical;
import com.lamnguyen.model.traditionalCipher.ITraditionalCipher;

import java.util.*;

public class KeyConfig {
    @Getter
    private Map<String, List<String>> mapAlgorithmSymmetrical, mapAlgorithmAsymmetrical, mapAlgorithmTraditional;
    private static KeyConfig instance;

    public static KeyConfig getInstance() {
        if (instance == null) instance = new KeyConfig();
        return instance;
    }

    private KeyConfig() {
        this.initMapAlgorithmSymmetrical();
        this.initMapAlgorithmAsymmetrical();
        this.initAlgorithmTraditional();
    }

    private void initMapAlgorithmSymmetrical() {
        mapAlgorithmSymmetrical = new TreeMap<>() {{
            put(ISymmetrical.Algorithms.DES.name(), new ArrayList<>() {{
                add("56");
            }});
            put(ISymmetrical.Algorithms.ARCFOUR.name(), new ArrayList<>() {{
                for (int i = 40; i <= 2048; i++)
                    add(String.valueOf(i));
            }});
            put(ISymmetrical.Algorithms.Blowfish.name(), new ArrayList<>() {{
                for (int i = 32; i <= 448; i++)
                    add(String.valueOf(i));
            }});
            put(ISymmetrical.Algorithms.ChaCha20.name(), new ArrayList<>() {{
                add("256");
            }});
            put(ISymmetrical.Algorithms.ChaCha20Poly1305.name(), new ArrayList<>() {{
                add("256");
            }});
            put(ISymmetrical.Algorithms.DESede.name(), new ArrayList<>() {{
                add("112");
                add("168");
            }});
            put(ISymmetrical.Algorithms.RC2.name(), new ArrayList<>() {{
                for (int i = 40; i <= 1024; i++)
                    add(String.valueOf(i));
            }});
            put(ISymmetrical.Algorithms.RC4.name(), new ArrayList<>() {{
                for (int i = 40; i <= 2048; i++)
                    add(String.valueOf(i));
            }});
            put(ISymmetrical.Algorithms.RC5.name(), new ArrayList<>() {{
                for (int i = 32; i <= 2040; i++)
                    add(String.valueOf(i));
            }});
            put(ISymmetrical.Algorithms.RC6.name(), new ArrayList<>() {{
                add("128");
                add("192");
                add("256");
            }});
            put(ISymmetrical.Algorithms.AES.name(), new ArrayList<>() {{
                add("128");
                add("192");
                add("256");
            }});
            put(ISymmetrical.Algorithms.Camellia.name(), new ArrayList<>() {{
                add("128");
                add("192");
                add("256");
            }});
        }};
    }

    private void initMapAlgorithmAsymmetrical() {
        mapAlgorithmAsymmetrical = new TreeMap<>() {{
            put(IAsymmetrical.KeyFactory.Algorithms.DiffieHellman.name(), new ArrayList<>() {
                {
                    add("1024");
                    add("2048");
                    add("3072");
                }
            });
            put(IAsymmetrical.KeyFactory.Algorithms.DSA.name(), new ArrayList<>() {
                {
                    add("1024");
                    add("2048");
                }
            });
            put(IAsymmetrical.KeyFactory.Algorithms.EC.name(), new ArrayList<>() {
                {
                    add("256");
                    add("384");
                    add("521");
                    add("571");
                }
            });
            put(IAsymmetrical.KeyFactory.Algorithms.EdDSA.name(), new ArrayList<>() {
                {
//                    add("256");
                    add("448");
                }
            });
//            put(IAsymmetrical.KeyFactory.Algorithms.Ed25519.name(), new ArrayList<>() {
//                {
//                    add("256");
//                }
//            });
            put(IAsymmetrical.KeyFactory.Algorithms.Ed448.name(), new ArrayList<>() {
                {
                    add("448");
                }
            });
            put(IAsymmetrical.KeyFactory.Algorithms.RSA.name(), new ArrayList<>() {
                {
                    add("1024");
                    add("2048");
                    add("3072");
                    add("4096");
                }
            });
//            put(IAsymmetrical.KeyFactory.Algorithms.RSASSA_PSS.name(), new ArrayList<>() {
//                {
//                    add("1024");
//                    add("2048");
//                    add("3072");
//                }
//            });
            put(IAsymmetrical.KeyFactory.Algorithms.XDH.name(), new ArrayList<>() {
                {
//                    add("256");
                    add("448");
                }
            });
//            put(IAsymmetrical.KeyFactory.Algorithms.X25519.name(), new ArrayList<>() {
//                {
//                    add("256");
//                }
//            });
            put(IAsymmetrical.KeyFactory.Algorithms.X448.name(), new ArrayList<>() {
                {
                    add("448");
                }
            });
        }};
    }

    private void initAlgorithmTraditional() {
        mapAlgorithmTraditional = new TreeMap<>() {{
            put(ITraditionalCipher.Algorithms.HILL.name(), new ArrayList<>() {{
                add("2");
                add("3");
            }});
            put(ITraditionalCipher.Algorithms.SHIFT.name(), new ArrayList<>());
            put(ITraditionalCipher.Algorithms.SUBSTITUTION.name(), new ArrayList<>());
            put(ITraditionalCipher.Algorithms.VIGENERE.name(), new ArrayList<>());
            put(ITraditionalCipher.Algorithms.AFFINE.name(), new ArrayList<>());
        }};
    }
}
