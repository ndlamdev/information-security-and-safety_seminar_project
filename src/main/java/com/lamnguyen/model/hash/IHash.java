/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:32 AM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.hash;

import com.lamnguyen.model.hash.impl.BCryptHashPassword;
import com.lamnguyen.model.hash.impl.HashImpl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface IHash {
    /**
     * Hash file
     *
     * @param src String đường dẫn đến file cần được hash
     * @return String chuổi hash
     */
    String hashFile(String src) throws IOException;

    /**
     * Hash văn bản
     *
     * @param data String chuổi ký tự cần được hash
     * @return String chuổi hash
     */
    String hashText(String data);


    /**
     * Lớp hổ trợ việc tạo ra đối tượng dùng để thực hiện các phương thức hash
     */
    class Factory {

        /**
         * Tạo đối tượng dùng để thực hiện việc hash
         *
         * @param alg String chuổi ký thuật toán cần tạo
         * @return IHash đối tượng dùng để hash
         */
        public static IHash getInstance(String alg) throws NoSuchAlgorithmException {
            return switch (alg) {
                case "BCrypt" -> BCryptHashPassword.getInstance();
                default -> HashImpl.getInstance(alg);
            };
        }
    }
}
