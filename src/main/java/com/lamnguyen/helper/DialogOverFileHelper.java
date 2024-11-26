/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:36 AM - 25/11/2024
 * User: kimin
 **/

package com.lamnguyen.helper;

import javax.swing.*;
import java.io.File;

public class DialogOverFileHelper {
    public static boolean overwriteFile(File file) {
        return !file.exists() || JOptionPane.showConfirmDialog(
                null,
                "Bạn có muốn ghi đề file không",
                "File đã tồn tại!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION;
    }

    public static boolean overwriteFile(String file) {
        return overwriteFile(new File(file));
    }

    public static boolean overwriteFile(String file, DialogProgressHelper.DialogProcess process) {
        return overwriteFile(new File(file), process);
    }

    public static boolean overwriteFile(File file, DialogProgressHelper.DialogProcess process) {
        if (overwriteFile(file)) return true;
        process.dispose();
        return false;
    }
}
