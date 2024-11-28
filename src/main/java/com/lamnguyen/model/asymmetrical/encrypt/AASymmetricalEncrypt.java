/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:28 AM - 29/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.asymmetrical.encrypt;

import com.lamnguyen.model.asymmetrical.AAsymmetrical;
import com.lamnguyen.model.asymmetrical.AsymmetricalKey;
import com.lamnguyen.model.symmetrical.ISymmetrical;
import com.lamnguyen.model.symmetrical.encrypt.ISymmetricalEncrypt;

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
    public final void loadKey(PublicKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        this.key = key;
        initCipher();
        cipher.init(Cipher.ENCRYPT_MODE, key);
    }

    @Override
    public final void encryptFile(ISymmetrical.Algorithms algorithm, String mode, String padding, String source, String dest) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        ISymmetricalEncrypt cipherEncrypt = initCipher(algorithm, mode, padding);
        var key = cipherEncrypt.getKey();
        var iv = cipherEncrypt.getIvSpec();
        DataOutputStream output = new DataOutputStream(new FileOutputStream(dest));
        ISymmetrical.save(output, key, iv);
        output.flush();
        output.writeLong(output.size() + 8);
        output.close();
        cipherEncrypt.encryptFile(source, dest, true);
    }

    private ISymmetricalEncrypt initCipher(ISymmetrical.Algorithms algorithm, String mode, String padding) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        return switch (algorithm) {
            case AES, AESWrap, AESWrapPad, Camellia ->
                    ISymmetrical.Factory.createEncrypt(algorithm, mode, padding, 128);
            case Blowfish -> ISymmetrical.Factory.createEncrypt(algorithm, mode, padding, 32);
            case ChaCha20, ChaCha20Poly1305, RC5, RC6 ->
                    ISymmetrical.Factory.createEncrypt(algorithm, mode, padding, 256);
            case DES -> ISymmetrical.Factory.createEncrypt(algorithm, mode, padding, 56);
            case DESede, DESedeWrap -> ISymmetrical.Factory.createEncrypt(algorithm, mode, padding, 112);
            case RC2, RC4, ARCFOUR -> ISymmetrical.Factory.createEncrypt(algorithm, mode, padding, 113);
        };
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