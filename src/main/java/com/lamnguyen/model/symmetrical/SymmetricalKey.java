/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:30 AM - 29/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public record SymmetricalKey(SecretKey key, IvParameterSpec iv) {
}