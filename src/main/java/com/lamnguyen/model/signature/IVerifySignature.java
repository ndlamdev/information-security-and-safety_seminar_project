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

    /**
     * xác nhận chữ ký file
     *
     * @param source    String đườn dẫn đến file cần xác thực chữ ký
     * @param signature String chuổi chữ ký đã ký.
     * @return boolean trả về true nếu chữ ký hợp lệ và ngược lại
     */
    boolean verifyFile(String source, String signature) throws IOException, SignatureException;


    /**
     * xác nhận chữ ký văn bản
     *
     * @param text      String chuổi ký tự cần xác thực chữ ký
     * @param signature String chuổi chữ ký đã ký.
     * @return boolean trả về true nếu chữ ký hợp lệ và ngược lại
     */
    boolean verifyText(String text, String signature) throws SignatureException;
}
