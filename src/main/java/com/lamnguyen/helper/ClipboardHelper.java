/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:35 AM - 16/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.helper;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class ClipboardHelper {
    public static void copy(String text, String contentNotify) {
        StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

        if (contentNotify != null && !contentNotify.isBlank())
            JOptionPane.showMessageDialog(null, contentNotify);
    }
}
