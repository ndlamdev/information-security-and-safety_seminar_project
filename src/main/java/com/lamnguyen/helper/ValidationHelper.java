/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:34 AM - 22/11/2024
 * User: kimin
 **/

package com.lamnguyen.helper;

import com.lamnguyen.security.asymmetrical.AsymmetricalKey;
import com.lamnguyen.security.symmetrical.SymmetricalKey;
import com.lamnguyen.security.traditionalCipher.TraditionalKey;
import com.lamnguyen.ui.component.selector.SelectCipherAlgorithmComponent;

import javax.swing.*;

public class ValidationHelper {
    public static boolean validateAlgorithm(SelectCipherAlgorithmComponent.Algorithm alg, DialogProgressHelper.DialogProcess process) {
        if (alg.padding() == null) {
            process.dispose();
            JOptionPane.showMessageDialog(null, "Vui lòng chọn padding nếu đã chọn mode!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (alg.mode() == null) {
            process.dispose();
            JOptionPane.showMessageDialog(null, "Vui lòng chọn mode nếu đã chọn padding!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean validateFile(String file, DialogProgressHelper.DialogProcess process) {
        if (file == null) {
            process.dispose();
            JOptionPane.showMessageDialog(null, "Chưa chọn file mã hóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


    public static boolean validateKey(SymmetricalKey key, DialogProgressHelper.DialogProcess process) {
        if (key == null) {
            process.dispose();
            JOptionPane.showMessageDialog(null, "Chưa nhập khóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static boolean validateKey(AsymmetricalKey key, DialogProgressHelper.DialogProcess process) {
        if (key == null) {
            process.dispose();
            JOptionPane.showMessageDialog(null, "Chưa nhập khóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static boolean validateKey(TraditionalKey<?> key, DialogProgressHelper.DialogProcess process) {
        if (key == null) {
            process.dispose();
            JOptionPane.showMessageDialog(null, "Chưa nhập khóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static boolean validateText(String text, DialogProgressHelper.DialogProcess process) {
        if (text == null) {
            process.dispose();
            JOptionPane.showMessageDialog(null, "Vui lòng nhập văn bảng mã hóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static boolean validateSignature(String signature, DialogProgressHelper.DialogProcess process) {
        if (signature == null) {
            process.dispose();
            JOptionPane.showMessageDialog(null, "Chưa nhập chữ ký!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
