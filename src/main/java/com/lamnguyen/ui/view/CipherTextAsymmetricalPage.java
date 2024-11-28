/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.config.CipherAlgorithmConfig;
import com.lamnguyen.helper.DialogOverFileHelper;
import com.lamnguyen.helper.DialogProgressHelper;
import com.lamnguyen.helper.SettingHelper;
import com.lamnguyen.helper.ValidationHelper;
import com.lamnguyen.model.asymmetrical.AsymmetricalKey;
import com.lamnguyen.model.asymmetrical.IAsymmetrical;
import com.lamnguyen.model.symmetrical.ISymmetrical;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.key.InputKeyComponent;
import com.lamnguyen.ui.component.selector.SelectCipherAlgorithmComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.security.*;
import java.util.Observable;
import java.util.Observer;

public class CipherTextAsymmetricalPage extends JPanel implements Observer {
    private final Application application;
    private OutputInputTextComponent inputTextComponent, outputTextComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectCipherAlgorithmComponent selectAlgorithmComponent;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private JButton action;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private final int V_GAP = 20;
    private boolean encryptMode = true;


    public CipherTextAsymmetricalPage(Application application) {
        this.application = application;
        this.init();
        this.event();
    }

    private void init() {
        this.setOpaque(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, V_GAP));

        inputTextComponent = new OutputInputTextComponent("Nhập văn bản");
        sizeController.addObserver(inputTextComponent);
        this.add(inputTextComponent);

        outputTextComponent = new OutputInputTextComponent("Văn bản đã mã hóa") {{
            setEditable(false);
            clickToCopy(true);
        }};
        sizeController.addObserver(outputTextComponent);
        this.add(outputTextComponent);

        inputKeyComponent = new InputKeyComponent(this::loadFileKey);
        this.add(inputKeyComponent);
        sizeController.addObserver(inputKeyComponent);

        selectAlgorithmComponent = new SelectCipherAlgorithmComponent(CipherAlgorithmConfig.getInstance().getAlgorithmAsymmetrical());
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        action = new JButton() {{
            setText("Mã hóa");
            setPreferredSize(new Dimension(500, 50));
            addActionListener(actionEvent -> {
                if (encryptMode) encrypt();
                else decrypt();
            });
        }};
        this.add(action);

        encryptMode();
    }

    private void event() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control D"), "Ctrl_D");
        getActionMap().put("Ctrl_D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CipherTextAsymmetricalPage.this.decryptMode();
            }
        });

        // Add Key Binding for Ctrl + E
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control E"), "Ctrl_E");
        getActionMap().put("Ctrl_E", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CipherTextAsymmetricalPage.this.encryptMode();
            }
        });
    }

    public void encryptMode() {
        encryptMode = true;
        action.setText("Mã hóa");
        publicKey = null;
        inputKeyComponent.setPathFileKey("");
        inputKeyComponent.setToolTipText("Nhập khóa công khai!");
        inputTextComponent.setTextJTextArea("");
        outputTextComponent.setTextJTextArea("");
    }

    public void decryptMode() {
        encryptMode = false;
        action.setText("Giải mã");
        privateKey = null;
        inputKeyComponent.setPathFileKey("");
        inputKeyComponent.setToolTipText("Nhập khóa riêng tư!");
        inputTextComponent.setTextJTextArea("");
        outputTextComponent.setTextJTextArea("");
    }

    private void encrypt() {
        DialogProgressHelper.runProcess(process -> {
            var text = inputTextComponent.getText();
            var alg = selectAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateAlgorithm(alg, process) || !ValidationHelper.validateKey(publicKey, process) || !ValidationHelper.validateText(text, process))
                return;
            var cipher = IAsymmetrical.Factory.createEncrypt(alg.algorithm(), alg.mode(), alg.padding());
            try {
                cipher.loadKey(publicKey);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     NoSuchProviderException e) {
                e.printStackTrace(System.out);
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
                process.dispose();
                return;
            }
            try {
                outputTextComponent.setTextJTextArea(cipher.encryptStringToBase64(text));
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            process.dispose();
        });
    }

    private void decrypt() {
        DialogProgressHelper.runProcess(process -> {
            var text = inputTextComponent.getText();
            var alg = selectAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateAlgorithm(alg, process) || !ValidationHelper.validateKey(privateKey, process) || !ValidationHelper.validateText(text, process))
                return;
            var cipher = IAsymmetrical.Factory.createDecrypt(alg.algorithm(), alg.mode(), alg.padding());
            try {
                cipher.loadKey(privateKey);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     NoSuchProviderException e) {
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
                process.dispose();
                return;
            }
            try {
                outputTextComponent.setTextJTextArea(cipher.decryptBase64ToString(text));
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (IllegalBlockSizeException | IllegalArgumentException | BadPaddingException e) {
                e.printStackTrace(System.out);
                JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            process.dispose();
        });
    }


    public Void loadFileKey(File file) {
        if (file == null) return null;
        try {
            if (encryptMode) publicKey = IAsymmetrical.KeyFactory.readPublicKey(file.getAbsolutePath());
            else privateKey = IAsymmetrical.KeyFactory.readPrivateKey(file.getAbsolutePath());
            inputKeyComponent.setPathFileKey(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Load key thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File key không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        var parentSize = getParent().getWidth();
        var sizeSpace = this.getHeight() - V_GAP * 6 - 110 - selectAlgorithmComponent.getHeight() - 50;
        inputTextComponent.setCustomSize(new Dimension(parentSize - 200, sizeSpace / 2));
        outputTextComponent.setCustomSize(new Dimension(parentSize - 200, sizeSpace / 2));
    }
}
