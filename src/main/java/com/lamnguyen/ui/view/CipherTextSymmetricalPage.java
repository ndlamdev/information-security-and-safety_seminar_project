/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.config.CipherAlgorithmConfig;
import com.lamnguyen.helper.ValidationHelper;
import com.lamnguyen.security.symmetrical.ISymmetrical;
import com.lamnguyen.security.symmetrical.SymmetricalKey;
import com.lamnguyen.security.symmetrical.decrypt.ISymmetricalDecrypt;
import com.lamnguyen.security.symmetrical.encrypt.ISymmetricalEncrypt;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.key.InputKeyComponent;
import com.lamnguyen.ui.component.selector.SelectCipherAlgorithmComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;
import com.lamnguyen.helper.DialogProgressHelper;
import lombok.Getter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CipherTextSymmetricalPage extends JPanel {
    private final Application application;
    private OutputInputTextComponent inputTextComponent, outputTextComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectCipherAlgorithmComponent selectAlgorithmComponent;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private JButton action;
    @Getter
    private SymmetricalKey key;


    public CipherTextSymmetricalPage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));

        inputTextComponent = new OutputInputTextComponent("Nhập văn bảng");
        sizeController.addObserver(inputTextComponent);
        this.add(inputTextComponent);

        outputTextComponent = new OutputInputTextComponent("Văn bản đã mã hóa") {{
            setEditable(false);
            clickToCopy(true);
        }};
        sizeController.addObserver(outputTextComponent);
        this.add(outputTextComponent);

        this.add(new JPanel() {{
            setPreferredSize(new Dimension(500, 50));
            setOpaque(false);
        }});

        inputKeyComponent = new InputKeyComponent(this::loadFileKey);
        this.add(inputKeyComponent);
        sizeController.addObserver(inputKeyComponent);

        selectAlgorithmComponent = new SelectCipherAlgorithmComponent(CipherAlgorithmConfig.getInstance().getAlgorithmSymmetrical());
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        action = new JButton() {{
            setPreferredSize(new Dimension(500, 50));
        }};
        this.add(action);
    }

    public void encryptMode() {
        if (action != null)
            this.remove(action);
        action = new JButton() {{
            setText("Mã hóa");
            setPreferredSize(new Dimension(500, 50));
            addActionListener(actionEvent -> encrypt());
        }};
        this.add(action);
        this.updateUI();
        this.repaint();
    }

    public void decryptMode() {
        if (action != null)
            this.remove(action);
        action = new JButton() {{
            setText("Giải hóa");
            setPreferredSize(new Dimension(500, 50));
            addActionListener(actionEvent -> decrypt());
        }};
        this.add(action);
        this.updateUI();
        this.repaint();
    }

    private void encrypt() {
        DialogProgressHelper.runProcess(process -> {
            var text = inputTextComponent.getText();
            var alg = selectAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateAlgorithm(alg, process) || !ValidationHelper.validateKey(key, process) || !ValidationHelper.validateText(text, process))
                return;
            ISymmetricalEncrypt cipher = null;
            try {
                cipher = ISymmetrical.Factory.createEncrypt(alg.algorithm(), alg.mode(), alg.padding(), key);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     InvalidAlgorithmParameterException e) {
                process.dispose();
                JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                outputTextComponent.setTextJTextArea(cipher.encryptStringBase64(text));
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                process.dispose();
                JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            process.dispose();
        });
    }

    private void decrypt() {
        DialogProgressHelper.runProcess(process -> {
            var text = inputTextComponent.getText();
            var alg = selectAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateAlgorithm(alg, process) || !ValidationHelper.validateKey(key, process) || !ValidationHelper.validateText(text, process))
                return;
            ISymmetricalDecrypt cipher = null;
            try {
                cipher = ISymmetrical.Factory.createDecrypt(alg.algorithm(), alg.mode(), alg.padding(), key);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }

            var data = cipher.decryptBase64ToString(text);
            process.dispose();
            if (data == null) {
                JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                outputTextComponent.setTextJTextArea(data);
                JOptionPane.showMessageDialog(null, "Thành công!");
            }
        });

    }

    public Void loadFileKey(File file) {
        if (file == null) return null;
        try {
            key = ISymmetrical.KeyFactory.readKey(file.getAbsolutePath());
            inputKeyComponent.setPathFileKey(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Load key thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File key không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }
}
