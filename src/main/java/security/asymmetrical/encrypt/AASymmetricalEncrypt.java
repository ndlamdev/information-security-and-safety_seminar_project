/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package main.java.security.asymmetrical.encrypt;

import main.java.security.asymmetrical.AAsymmetrical;
import main.java.security.asymmetrical.AsymmetricalKey;
import main.java.security.symmetrical.ISymmetrical;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public abstract class AASymmetricalEncrypt extends AAsymmetrical implements IASymmetricalEncrypt {
    protected PublicKey key;

    public AASymmetricalEncrypt(PublicKey key) {
        this.key = key;
    }

    public AASymmetricalEncrypt() {
    }

    @Override
    public final void loadKey(PublicKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.key = key;
        initCipher();
        cipher.init(Cipher.ENCRYPT_MODE, key);
    }

    @Override
    public final void encryptFile(ISymmetrical.Algorithms algorithm, int sizeKey, String source, String dest) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        var cipherEncrypt = ISymmetrical.Factory.createEncrypt(algorithm, null, null, sizeKey);
        var key = cipherEncrypt.getKey();
        DataOutputStream output = new DataOutputStream(new FileOutputStream(dest));
        String base64Encode = encryptStringToBase64(ISymmetrical.encodeKeyToBase64(key));
        output.writeUTF(algorithm.name());
        output.writeUTF(base64Encode);
        output.writeLong(output.size() + 8L);
        output.close();
        cipherEncrypt.encryptFile(source, dest, true);
    }

    protected abstract KeyPairGenerator initKeyPairGenerator() throws NoSuchAlgorithmException;

    @Override
    public final AsymmetricalKey generateKey(int size) throws NoSuchAlgorithmException {
        KeyPairGenerator generator = initKeyPairGenerator();
        generator.initialize(size);
        var keyPair = generator.generateKeyPair();
        return new AsymmetricalKey(keyPair.getPrivate(), keyPair.getPublic());
    }

    @Override
    public final byte[] encrypt(String data) throws IllegalBlockSizeException, BadPaddingException {
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public final String encryptToBase64(byte[] data) throws IllegalBlockSizeException, BadPaddingException {
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    @Override
    public final String encryptStringToBase64(String data) throws IllegalBlockSizeException, BadPaddingException {
        return Base64.getEncoder().encodeToString(encrypt(data));
    }
}