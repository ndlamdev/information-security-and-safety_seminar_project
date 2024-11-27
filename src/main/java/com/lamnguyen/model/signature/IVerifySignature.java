/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:16 PM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.signature;

import java.io.IOException;
import java.security.SignatureException;

public interface IVerifySignature {
    boolean verifyFile(String source, String signature) throws IOException, SignatureException;

    boolean verifyText(String text, String signature) throws SignatureException;
}
