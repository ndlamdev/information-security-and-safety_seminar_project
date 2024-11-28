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

public interface ISign {
    /**
     * Ký file
     *
     * @param source String đường dẫn đến file cần ký
     */
    String signFile(String source) throws IOException, SignatureException;

    /**
     * Ký văn bản
     *
     * @param text String chuổi ký tự cần được ký
     */
    String signText(String text) throws SignatureException;
}
