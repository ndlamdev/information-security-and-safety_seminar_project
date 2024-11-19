/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.config.CipherAlgorithmConfig;
import com.lamnguyen.security.asymmetrical.AsymmetricalKey;
import com.lamnguyen.security.asymmetrical.IAsymmetrical;
import com.lamnguyen.security.symmetrical.SymmetricalKey;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.key.InputKeyComponent;
import com.lamnguyen.ui.component.selector.SelectCipherAlgorithmComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CipherTextAsymmetricalPage extends JPanel {
    private final Application application;
    private OutputInputTextComponent inputTextComponent, outputTextComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectCipherAlgorithmComponent selectAlgorithmComponent;
    private boolean encrypt = true;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private JButton action;
    private AsymmetricalKey key;


    public CipherTextAsymmetricalPage(Application application) {
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

        selectAlgorithmComponent = new SelectCipherAlgorithmComponent(CipherAlgorithmConfig.getInstance().getAlgorithmAsymmetrical());
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

    }

    private void decrypt() {
    }

    private boolean validate(String file, SymmetricalKey key) {
        if (file == null) {
            JOptionPane.showMessageDialog(null, "Chưa chọn file mã hóa!", "Error", JOptionPane.ERROR_MESSAGE);
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
            key = IAsymmetrical.KeyFactory.readKey(file.getAbsolutePath());
            inputKeyComponent.setPathFileKey(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Load key thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File key không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

}
