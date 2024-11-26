/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:06 PM - 26/11/2024
 * User: kimin
 **/

package com.lamnguyen.model.symmetrical;

import com.lamnguyen.config.CipherAlgorithmConfig;
import com.lamnguyen.model.symmetrical.decrypt.ISymmetricalDecrypt;
import com.lamnguyen.model.symmetrical.encrypt.ISymmetricalEncrypt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

public class SymmetricalTest {
    private static ISymmetricalEncrypt encrypt;
    private static ISymmetricalDecrypt decrypt;

    private static SymmetricalKey initKey(String algorithms, int sizeKey) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        encrypt = ISymmetrical.Factory.createEncrypt(algorithms, null, null, sizeKey);
        return new SymmetricalKey(encrypt.getKey(), encrypt.getIvSpec());
    }

    public static void allTest(String algorithms, int sizeKey) throws Exception {
        var key = initKey(algorithms, sizeKey);

        String input = "Xin chào";
        var optional = CipherAlgorithmConfig.getInstance().getAlgorithmSymmetrical().stream().filter(it -> it.alg().equals(algorithms)).findFirst();
        if (optional.isEmpty()) return;
        var modes = optional.get().modes();
        var paddings = optional.get().paddings();
        List<String> errorAlg = new ArrayList<>();
        test(key, algorithms, "", "", input, errorAlg);
        for (var mode : modes) {
            for (var padding : paddings) {
                if (mode.isBlank() || padding.isBlank()) continue;
                test(key, algorithms, mode, padding, input, errorAlg);
            }
        }
        Assertions.assertEquals(errorAlg.size(), 0);
    }

    private static void test(SymmetricalKey key, String algorithms, String mode, String padding, String input, List<String> errorAlg) {
        String extension = (mode.isBlank() ? "" : "/" + mode) + (padding.isBlank() ? "" : "/" + padding);
        try {
            encrypt = ISymmetrical.Factory.createEncrypt(algorithms, mode, padding, key);
            decrypt = ISymmetrical.Factory.createDecrypt(algorithms, mode, padding, key);
            var de = decrypt.decryptBase64ToString(encrypt.encryptStringBase64(input));
            System.out.println(algorithms + extension + " ---> " + input + " -> " + de);
            Assertions.assertEquals(input, de);
        } catch (org.opentest4j.AssertionFailedError | Exception e) {
            errorAlg.add(algorithms + extension);
        }
    }
}
