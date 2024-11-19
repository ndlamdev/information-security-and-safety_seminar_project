/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:16 PM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.security.signature;

import java.io.IOException;
import java.security.SignatureException;

public interface ISignFile {
    String sign(String source) throws IOException, SignatureException;
}
