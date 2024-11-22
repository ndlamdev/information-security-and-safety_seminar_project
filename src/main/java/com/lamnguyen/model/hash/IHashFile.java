/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:32 AM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.hash;

import java.io.IOException;

public interface IHashFile {
    String hash(String src) throws IOException;
}
