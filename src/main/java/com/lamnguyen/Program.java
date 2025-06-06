/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:06 AM - 12/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen;

import com.lamnguyen.ui.Application;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class Program {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        Application.run();
    }
}
