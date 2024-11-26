/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:34 AM - 22/11/2024
 * User: kimin
 **/

package com.lamnguyen.helper;

import com.lamnguyen.model.asymmetrical.AsymmetricalKey;
import com.lamnguyen.model.symmetrical.SymmetricalKey;
import com.lamnguyen.model.traditionalCipher.TraditionalKey;
import com.lamnguyen.ui.component.selector.SelectCipherAlgorithmComponent;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.File;

public class ValidationHelper {
    public static boolean validateAlgorithm(SelectCipherAlgorithmComponent.Algorithm alg, DialogProgressHelper.DialogProcess process) {
        if (alg.mode() == null && alg.padding() == null) return true;

        if (alg.padding() == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn padding nếu đã chọn mode!", "Error", JOptionPane.ERROR_MESSAGE);
            process.dispose();
            return false;
        }

        if (alg.mode() == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn mode nếu đã chọn padding!", "Error", JOptionPane.ERROR_MESSAGE);
            process.dispose();
            return false;
        }

        return true;
    }

    public static boolean validateFile(String file, DialogProgressHelper.DialogProcess process) {
        if (file != null) return true;

        JOptionPane.showMessageDialog(null, "Chưa chọn file mã hóa!", "Error", JOptionPane.ERROR_MESSAGE);
        process.dispose();
        return false;
    }

    public static boolean validateFile(File file, DialogProgressHelper.DialogProcess process) {
        if (file != null) return true;

        JOptionPane.showMessageDialog(null, "Chưa chọn file mã hóa!", "Error", JOptionPane.ERROR_MESSAGE);
        process.dispose();
        return false;
    }


    public static boolean validateKey(SymmetricalKey key, DialogProgressHelper.DialogProcess process) {
        if (key != null) return true;

        JOptionPane.showMessageDialog(null, "Chưa nhập khóa!", "Error", JOptionPane.ERROR_MESSAGE);
        process.dispose();
        return false;
    }

    public static boolean validateKey(AsymmetricalKey key, DialogProgressHelper.DialogProcess process) {
        if (key != null) return true;

        JOptionPane.showMessageDialog(null, "Chưa nhập khóa!", "Error", JOptionPane.ERROR_MESSAGE);
        process.dispose();
        return false;
    }

    public static boolean validateKey(TraditionalKey<?> key, DialogProgressHelper.DialogProcess process) {
        if (key != null) return true;

        JOptionPane.showMessageDialog(null, "Chưa nhập khóa!", "Error", JOptionPane.ERROR_MESSAGE);
        process.dispose();
        return false;
    }

    public static boolean validateText(String text, DialogProgressHelper.DialogProcess process) {
        if (text != null) return true;

        JOptionPane.showMessageDialog(null, "Vui lòng nhập văn bảng mã hóa!", "Error", JOptionPane.ERROR_MESSAGE);
        process.dispose();
        return false;
    }

    public static boolean validateSignature(String signature, DialogProgressHelper.DialogProcess process) {
        if (signature != null) return true;

        JOptionPane.showMessageDialog(null, "Chưa nhập chữ ký!", "Error", JOptionPane.ERROR_MESSAGE);
        process.dispose();
        return false;
    }

    public static boolean validateKeyGenerate(AsymmetricalKey key, DialogProgressHelper.DialogProcess process) {
        if (key != null) return true;

        JOptionPane.showMessageDialog(null, "Vui lòng tạo khóa trước!", "Error", JOptionPane.ERROR_MESSAGE);
        process.dispose();
        return false;
    }

    public static boolean validateKeyGenerate(SecretKey key, DialogProgressHelper.DialogProcess process) {
        if (key != null) return true;

        JOptionPane.showMessageDialog(null, "Vui lòng tạo khóa trước!", "Error", JOptionPane.ERROR_MESSAGE);
        process.dispose();
        return false;
    }

    public static boolean validateKeyGenerate(TraditionalKey<?> key, DialogProgressHelper.DialogProcess process) {
        if (key != null) return true;

        JOptionPane.showMessageDialog(null, "Vui lòng tạo khóa trước!", "Error", JOptionPane.ERROR_MESSAGE);
        process.dispose();
        return false;
    }
}
