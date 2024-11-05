/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:49 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.server.security.symmetrical;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public abstract class ASymmetrical implements ISymmetrical {
    protected SecretKey key;
    protected Cipher cipher;

    @Override
    public void loadKey(SecretKey key) {
        this.key = key;
    }

    @Override
    public SecretKey getKey() {
        return this.key;
    }
}