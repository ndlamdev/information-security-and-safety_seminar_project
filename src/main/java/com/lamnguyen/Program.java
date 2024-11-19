/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:06 AM - 12/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen;

import com.lamnguyen.ui.Application;

import javax.swing.*;
import java.io.IOException;

public class Program {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        Application.setup();
        new Application();
    }
}
