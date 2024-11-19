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
import com.lamnguyen.security.signature.ISignFile;
import com.lamnguyen.security.signature.impl.SignFileImpl;
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

public class SignFilePage extends JPanel {
    private final Application application;
    private DropAndDragComponent dropAndDragComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectSignatureAlgorithmComponent selectAlgorithmComponent;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private AsymmetricalKey key;
    private OutputInputTextComponent outputComponent;


    public SignFilePage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));

        dropAndDragComponent = new DropAndDragComponent(new Dimension(800, 250));
        sizeController.addObserver(dropAndDragComponent);
        this.add(dropAndDragComponent);

        inputKeyComponent = new InputKeyComponent(this::loadFileKey);
        this.add(inputKeyComponent);
        sizeController.addObserver(inputKeyComponent);

        selectAlgorithmComponent = new SelectSignatureAlgorithmComponent(SignatureAlgorithmConfig.getInstance().getAlgorithmSignature());
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        this.add(new JButton("Ký") {{
            setPreferredSize(new Dimension(500, 50));
            addActionListener(action -> signFile());
        }});

        outputComponent = new OutputInputTextComponent("Chữ kí file!") {{
            setEditable(false);
            clickToCopy(true);
        }};
        this.add(outputComponent);
        sizeController.addObserver(outputComponent);
    }


    private void signFile() {
        var file = dropAndDragComponent.getPathFile();
        var alg = selectAlgorithmComponent.getAlgorithm();
        if (!validate(file, key)) return;

        ISignFile signer = null;
        try {
            signer = SignFileImpl.getInstance(alg, key.privateKey());
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            outputComponent.setTextJTextArea(signer.sign(file));
            JOptionPane.showMessageDialog(null, "Thành công!");
        } catch (IOException | SignatureException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validate(String file, AsymmetricalKey key) {
        if (file == null) {
            JOptionPane.showMessageDialog(null, "Chưa chọn file ký!", "Error", JOptionPane.ERROR_MESSAGE);
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
