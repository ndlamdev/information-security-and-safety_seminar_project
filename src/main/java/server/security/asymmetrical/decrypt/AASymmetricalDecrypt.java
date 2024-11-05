/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.asymmetrical.decrypt;

import main.java.server.security.symmetrical.ISymmetrical;
import main.java.server.security.symmetrical.decrypt.AESDecrypt;
import main.java.server.security.symmetrical.decrypt.DESDecrypt;
import main.java.server.security.symmetrical.decrypt.ISymmetricalDecrypt;

import javax.crypto.SecretKey;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class AASymmetricalDecrypt implements IASymmetricalDecrypt {

    @Override
    public boolean decryptFile(String source, String dest) throws Exception {
        HeaderFileEncrypt header = loadHeader(source);
        if (header == null) {
            return false;
        }
        return decryptFileHelper(header, source, dest);
    }

    private HeaderFileEncrypt loadHeader(String source) throws Exception {
        DataInputStream input;
        try {
            input = new DataInputStream(new FileInputStream(source));
        } catch (FileNotFoundException e) {
            return null;
        }
        try {
            String algorithm = input.readUTF();
            String base64Encode = input.readUTF();
            long skip = input.readLong();
            input.close();
            ISymmetrical.SymmetricalAlgorithm alg;
            try {
                alg = ISymmetrical.SymmetricalAlgorithm.valueOf(algorithm);
            } catch (Exception e) {
                return null;
            }
            String decodeKey = decryptBase64ToString(base64Encode);
            SecretKey key = ISymmetrical.generateKeyByEncodeKey(decodeKey, algorithm);
            if (key == null) {
                return null;
            }
            return new HeaderFileEncrypt(key, alg, skip);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean decryptFileHelper(HeaderFileEncrypt header, String source, String dest) {
        return initSymmetricalDecrypt(header.algorithm, header.key).decryptFile(source, dest, header.skip);
    }

    private ISymmetricalDecrypt initSymmetricalDecrypt(ISymmetrical.SymmetricalAlgorithm algorithm, SecretKey key) {
        return switch (algorithm) {
            case AES -> new AESDecrypt(key);
            case DES -> new DESDecrypt(key);
            default -> throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        };
    }

    private record HeaderFileEncrypt(SecretKey key, ISymmetrical.SymmetricalAlgorithm algorithm, long skip) {
    }
}