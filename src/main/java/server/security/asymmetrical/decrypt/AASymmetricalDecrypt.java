/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.asymmetrical.decrypt;

import main.java.server.security.asymmetrical.AAsymmetrical;
import main.java.server.security.symmetrical.ISymmetrical;
import main.java.server.security.symmetrical.decrypt.AESDecrypt;
import main.java.server.security.symmetrical.decrypt.DESDecrypt;
import main.java.server.security.symmetrical.decrypt.ISymmetricalDecrypt;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

public abstract class AASymmetricalDecrypt extends AAsymmetrical implements IASymmetricalDecrypt {
    protected PrivateKey key;

    public AASymmetricalDecrypt(PrivateKey key) throws Exception {
        this.loadKey(key);
    }

    public AASymmetricalDecrypt() {
    }

    @Override
    public final boolean decryptFile(String source, String dest) throws Exception {
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

    private boolean decryptFileHelper(HeaderFileEncrypt header, String source, String dest) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return ISymmetrical.Factory.createDecrypt(header.algorithm, header.key).decryptFile(source, dest, header.skip);
    }

    @Override
    public final void loadKey(PrivateKey key) throws Exception {
        this.key = key;
        initCipher();
        cipher.init(Cipher.DECRYPT_MODE, key);
    }

    private record HeaderFileEncrypt(SecretKey key, ISymmetrical.SymmetricalAlgorithm algorithm, long skip) {
    }

    @Override
    public final byte[] decrypt(byte[] data) throws Exception {
        return cipher.doFinal(data);
    }

    @Override
    public final String decryptToString(byte[] data) throws Exception {
        return new String(decrypt(data), StandardCharsets.UTF_8);
    }

    @Override
    public final String decryptBase64ToString(String base64) throws Exception {
        return decryptToString(Base64.getDecoder().decode(base64));
    }
}