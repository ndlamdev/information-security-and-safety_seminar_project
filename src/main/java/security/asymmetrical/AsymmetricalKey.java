/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:30 AM - 29/10/2024
 * User: lam-nguyen
 **/

package main.java.security.asymmetrical;

import java.security.PrivateKey;
import java.security.PublicKey;

public record AsymmetricalKey(PrivateKey privateKey, PublicKey publicKey) {
}