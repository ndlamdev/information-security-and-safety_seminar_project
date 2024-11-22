/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.helper.ValidationHelper;
import com.lamnguyen.security.traditionalCipher.ITraditionalCipher;
import com.lamnguyen.security.traditionalCipher.TraditionalKey;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.key.InputKeyComponent;
import com.lamnguyen.ui.component.selector.SelectCipherTraditionalAlgorithmComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;
import com.lamnguyen.helper.DialogProgressHelper;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.function.Function;

public class CipherTextTraditionalPage extends JPanel {
    private final Application application;
    private OutputInputTextComponent inputTextComponent, outputTextComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectCipherTraditionalAlgorithmComponent selectAlgorithmComponent;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private JButton action;
    @Getter
    private TraditionalKey<?> key;
    private Function<SelectCipherTraditionalAlgorithmComponent.Algorithm, Void> onAlgChange;


    public CipherTextTraditionalPage(Application application) {
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

        onAlgChange = algorithm -> {
            key = null;
            return null;
        };
        selectAlgorithmComponent = new SelectCipherTraditionalAlgorithmComponent(onAlgChange);
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);
    }

    public void encryptMode() {
        if (action != null)
            this.remove(action);
        action = new JButton() {{
            setPreferredSize(new Dimension(500, 50));
            setText("Mã hóa");
            addActionListener(actionEvent -> doFinal(ITraditionalCipher.SecureMode.ENCRYPT));
        }};
        this.add(action);
        this.updateUI();
        this.repaint();
    }

    public void decryptMode() {
        if (action != null)
            this.remove(action);
        action = new JButton() {{
            setPreferredSize(new Dimension(500, 50));
            setText("Giải hóa");
            addActionListener(actionEvent -> doFinal(ITraditionalCipher.SecureMode.DECRYPT));
        }};
        this.add(action);
        this.updateUI();
        this.repaint();
    }


    private void doFinal(ITraditionalCipher.SecureMode mode) {
        DialogProgressHelper.runProcess(process -> {
            var text = inputTextComponent.getText();
            var alg = selectAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateKey(key, process) || !ValidationHelper.validateText(text, process))
                return;
            var cipher = ITraditionalCipher.Factory.createEncrypt(alg.algorithm(), alg.language());
            try {
                cipher.loadKey(key);
                cipher.init(mode);
            } catch (Exception e) {
                e.printStackTrace(System.out);
                process.dispose();
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            process.dispose();
            try {
                outputTextComponent.setTextJTextArea(cipher.doFinal(text));
                process.dispose();
                JOptionPane.showMessageDialog(null, "Thành công");
            } catch (Exception e) {
                process.dispose();
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public Void loadFileKey(File file) {
        if (file == null) return null;
        var alg = selectAlgorithmComponent.getAlgorithm();
        try {
            key = ITraditionalCipher.Factory.createEncrypt(alg.algorithm(), alg.language()).readKey(file.getAbsolutePath());
            inputKeyComponent.setPathFileKey(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Load key thành công!");
        } catch (Exception e) {
            e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "File key không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }
}
