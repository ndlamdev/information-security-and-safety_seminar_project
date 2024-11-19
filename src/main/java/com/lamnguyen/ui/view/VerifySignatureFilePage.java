/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.config.SignatureAlgorithmConfig;
import com.lamnguyen.security.asymmetrical.AsymmetricalKey;
import com.lamnguyen.security.asymmetrical.IAsymmetrical;
import com.lamnguyen.security.signature.IVerifySignatureFile;
import com.lamnguyen.security.signature.impl.VerifySignatureFileImpl;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.dropAndDrag.DropAndDragComponent;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.key.InputKeyComponent;
import com.lamnguyen.ui.component.selector.SelectSignatureAlgorithmComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

public class VerifySignatureFilePage extends JPanel {
    private final Application application;
    private DropAndDragComponent dropAndDragComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectSignatureAlgorithmComponent selectAlgorithmComponent;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private AsymmetricalKey key;
    private OutputInputTextComponent inputComponent;


    public VerifySignatureFilePage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));

        dropAndDragComponent = new DropAndDragComponent(new Dimension(800, 250));
        sizeController.addObserver(dropAndDragComponent);
        this.add(dropAndDragComponent);

        inputComponent = new OutputInputTextComponent("Chữ kí file!");
        this.add(inputComponent);
        sizeController.addObserver(inputComponent);

        inputKeyComponent = new InputKeyComponent(this::loadFileKey);
        this.add(inputKeyComponent);
        sizeController.addObserver(inputKeyComponent);

        selectAlgorithmComponent = new SelectSignatureAlgorithmComponent(SignatureAlgorithmConfig.getInstance().getAlgorithmSignature());
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        this.add(new JButton("Xác thực") {{
            setPreferredSize(new Dimension(500, 50));
            addActionListener(action -> verifySignatureFile());
        }});
    }


    private void verifySignatureFile() {
        var file = dropAndDragComponent.getPathFile();
        var alg = selectAlgorithmComponent.getAlgorithm();
        var signature = inputComponent.getText();
        if (!validate(file, key, signature)) return;

        IVerifySignatureFile verifier = null;
        try {
            verifier = VerifySignatureFileImpl.getInstance(alg, key.publicKey());
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            if (verifier.verify(file, signature))
                JOptionPane.showMessageDialog(null, "Xác thực chữ ký file thành công!");
            else throw new SignatureException("Lỗi");
        } catch (IOException | SignatureException e) {
            JOptionPane.showMessageDialog(null, "Xác thực chữ ký file thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validate(String file, AsymmetricalKey key, String signature) {
        if (file == null) {
            JOptionPane.showMessageDialog(null, "Chưa chọn file ký!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (key == null) {
            JOptionPane.showMessageDialog(null, "Chưa nhập khóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (signature == null) {
            JOptionPane.showMessageDialog(null, "Chưa nhập chữ ký!", "Error", JOptionPane.ERROR_MESSAGE);
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
