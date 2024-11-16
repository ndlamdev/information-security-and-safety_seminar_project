/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.view;

import lombok.Getter;
import main.java.config.CipherAlgorithmConfig;
import main.java.security.symmetrical.ISymmetrical;
import main.java.security.symmetrical.SymmetricalKey;
import main.java.security.symmetrical.decrypt.ISymmetricalDecrypt;
import main.java.security.symmetrical.encrypt.ISymmetricalEncrypt;
import main.java.ui.Application;
import main.java.ui.component.input.OutputInputTextComponent;
import main.java.ui.component.key.InputKeyComponent;
import main.java.ui.component.selector.SelectCipherAlgorithmComponent;
import main.java.ui.controller.SubjectSizeController;

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
    private boolean encrypt = true;
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

        encryptMode();
    }

    public void encryptMode() {
        encrypt = true;
        action.setText("Mã hóa");
        action.addActionListener(actionEvent -> encrypt());
    }

    public void decryptMode() {
        encrypt = false;
        action.setText("Giải hóa");
        action.addActionListener(actionEvent -> decrypt());
    }

    private void encrypt() {
        var text = inputTextComponent.getText();
        var alg = selectAlgorithmComponent.getAlgorithm();
        if (!validate(text, key)) return;
        ISymmetricalEncrypt cipher = null;
        try {
            cipher = ISymmetrical.Factory.createEncrypt(alg.algorithm(), alg.mode(), alg.padding(), key);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        try {
            outputTextComponent.setTextJTextArea(cipher.encryptStringBase64(text));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Thành công!");
        }
    }

    private void decrypt() {
        var text = inputTextComponent.getText();
        var alg = selectAlgorithmComponent.getAlgorithm();
        if (!validate(text, key)) return;
        ISymmetricalDecrypt cipher = null;
        try {
            cipher = ISymmetrical.Factory.createDecrypt(alg.algorithm(), alg.mode(), alg.padding(), key);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        var data = cipher.decryptBase64ToString(text);
        if (data == null) {
            JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            outputTextComponent.setTextJTextArea(data);
            JOptionPane.showMessageDialog(null, "Thành công!");
        }
    }

    private boolean validate(String file, SymmetricalKey key) {
        if (file == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập văn bảng mã hóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (key == null) {
            JOptionPane.showMessageDialog(null, "Chưa nhập khóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
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
