/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.asymmetrical.encrypt;

import main.java.server.security.asymmetrical.AAsymmetrical;
import main.java.server.security.asymmetrical.ASymmetricalKey;
import main.java.server.security.symmetrical.ISymmetrical;
import main.java.server.security.symmetrical.encrypt.AESEncrypt;
import main.java.server.security.symmetrical.encrypt.DESEncrypt;
import main.java.server.security.symmetrical.encrypt.ISymmetricalEncrypt;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
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
    public final boolean encryptFile(ISymmetrical.SymmetricalAlgorithm algorithm, int sizeKey, String source, String dest) throws Exception {
        var cipherEncrypt = ISymmetrical.Factory.createEncrypt(algorithm, sizeKey);
        var key = cipherEncrypt.getKey();
        try {
            DataOutputStream output = new DataOutputStream(new FileOutputStream(dest));
            String base64Encode = encryptStringToBase64(ISymmetrical.encodeKeyToBase64(key));
            output.writeUTF(algorithm.name());
            output.writeUTF(base64Encode);
            output.writeLong(output.size() + 8L);
            output.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return encryptFileHelper(cipherEncrypt, source, dest);
    }

    private boolean encryptFileHelper(ISymmetricalEncrypt encrypt, String source, String dest) {
        return encrypt.encryptFile(source, dest, true);
    }

    protected abstract KeyPairGenerator initKeyPairGenerator() throws NoSuchAlgorithmException;

    @Override
    public final ASymmetricalKey generateKey(int size) throws Exception {
        KeyPairGenerator generator = initKeyPairGenerator();
        generator.initialize(size);
        var keyPair = generator.generateKeyPair();
        return new ASymmetricalKey(keyPair.getPrivate(), keyPair.getPublic());
    }

    @Override
    public final byte[] encrypt(String data) throws Exception {
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public final String encryptToBase64(byte[] data) throws Exception {
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    @Override
    public final String encryptStringToBase64(String data) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(data));
    }
}