/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:22 AM - 05/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.asymmetrical;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public abstract class AAsymmetrical implements IAsymmetrical {
    protected Cipher cipher;
    protected String mode, padding;

    protected abstract void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException;

    protected String getExtension() {
        return (mode == null || mode.isBlank() ? "" : "/" + mode) + (padding == null || padding.isBlank() ? "" : "/" + padding);
    }
}
