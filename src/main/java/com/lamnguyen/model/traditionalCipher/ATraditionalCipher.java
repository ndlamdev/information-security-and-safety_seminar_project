/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:27 AM - 28/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.traditionalCipher;

import com.lamnguyen.config.CharSetConfig;

public abstract class ATraditionalCipher extends ATraditionalCipherImpl implements ITraditionalCipher {
    protected final ITraditionalCipher.SecureLanguage language;

    protected ATraditionalCipher(TraditionalKey<?> key, SecureLanguage language) {
        super(key, CharSetConfig.getMapChar(language));
        this.language = language;
    }

    protected ATraditionalCipher(SecureLanguage language) {
        super(null, CharSetConfig.getMapChar(language));
        this.language = language;
    }
}
