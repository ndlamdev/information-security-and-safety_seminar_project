/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.config.SignatureAlgorithmConfig;
import com.lamnguyen.helper.DialogProgressHelper;
import com.lamnguyen.helper.ValidationHelper;
import com.lamnguyen.model.asymmetrical.AsymmetricalKey;
import com.lamnguyen.model.asymmetrical.IAsymmetrical;
import com.lamnguyen.model.signature.IVerifySignature;
import com.lamnguyen.model.signature.impl.VerifySignatureFileImpl;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.dropAndDrag.DropAndDragComponent;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.key.InputKeyComponent;
import com.lamnguyen.ui.component.selector.SelectSignatureAlgorithmComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.security.*;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class VerifySignaturePage extends JPanel implements Observer {
    private final Application application;
    private DropAndDragComponent dropAndDragComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectSignatureAlgorithmComponent selectAlgorithmComponent;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private PublicKey publicKey;
    private OutputInputTextComponent inputSignComponent;
    private final int V_GAP = 20;
    private boolean fileMode;
    private JButton action;
    private OutputInputTextComponent inputTextComponent;
    private JPanel panelInput;
    private CardLayout cardLayoutPanelInput;


    public VerifySignaturePage(Application application) {
        this.application = application;
        this.init();
        this.event();
    }

    private void init() {
        this.setOpaque(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, V_GAP));

        cardLayoutPanelInput = new CardLayout();
        panelInput = new JPanel(cardLayoutPanelInput) {{
            setOpaque(false);
        }};
        this.add(panelInput);

        dropAndDragComponent = new DropAndDragComponent(new Dimension(800, 250));
        panelInput.add(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)) {{
            setOpaque(false);
            add(dropAndDragComponent);
        }}, "file");

        inputTextComponent = new OutputInputTextComponent("Nhập văn bản");
        panelInput.add(inputTextComponent, "text");

        inputSignComponent = new OutputInputTextComponent("Chữ kí file!");
        this.add(inputSignComponent);

        inputKeyComponent = new InputKeyComponent(this::loadFileKey);
        this.add(inputKeyComponent);
        sizeController.addObserver(inputKeyComponent);

        selectAlgorithmComponent = new SelectSignatureAlgorithmComponent(SignatureAlgorithmConfig.getInstance().getAlgorithmSignature());
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        action = new JButton() {{
            setPreferredSize(new Dimension(500, 50));
            addActionListener(action -> {
                if (fileMode) verifySignatureFile();
                else verifySignatureText();
            });
        }};
        this.add(action);
    }

    private void event() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control F"), "Ctrl_F");
        getActionMap().put("Ctrl_F", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VerifySignaturePage.this.verifySignatureFileMode();
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control T"), "Ctrl_T");
        getActionMap().put("Ctrl_T", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VerifySignaturePage.this.verifySignatureTextMode();
            }
        });
    }

    private void verifySignatureFile() {
        DialogProgressHelper.runProcess(process -> {
            var file = dropAndDragComponent.getPathFile();
            var alg = selectAlgorithmComponent.getAlgorithm();
            var signature = inputSignComponent.getText();
            if (!ValidationHelper.validateFile(file, process) || !ValidationHelper.validateKey(publicKey, process) || !ValidationHelper.validateSignature(signature, process))
                return;

            try {
                IVerifySignature verifier = VerifySignatureFileImpl.getInstance(alg, publicKey);
                if (verifier.verifyFile(file, signature)) {
                    JOptionPane.showMessageDialog(null, "Xác thực chữ ký file thành công!");
                    process.dispose();
                } else throw new SignatureException();

            } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
                process.dispose();
            } catch (IOException | SignatureException e) {
                JOptionPane.showMessageDialog(null, "Xác thực chữ ký file thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
                process.dispose();
            }
        });
    }

    private void verifySignatureText() {
        DialogProgressHelper.runProcess(process -> {
            var text = inputTextComponent.getText();
            var alg = selectAlgorithmComponent.getAlgorithm();
            var signature = inputSignComponent.getText();
            if (!ValidationHelper.validateText(text, process) || !ValidationHelper.validateKey(publicKey, process) || !ValidationHelper.validateSignature(signature, process))
                return;

            try {
                IVerifySignature verifier = VerifySignatureFileImpl.getInstance(alg, publicKey);
                if (verifier.verifyText(text, signature)) {
                    JOptionPane.showMessageDialog(null, "Xác thực chữ ký file thành công!");
                    process.dispose();
                } else throw new SignatureException();

            } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
                process.dispose();
            } catch (SignatureException e) {
                JOptionPane.showMessageDialog(null, "Xác thực chữ ký file thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
                process.dispose();
            }
        });
    }

    public Void loadFileKey(File file) {
        if (file == null) return null;
        try {
            publicKey = IAsymmetrical.KeyFactory.readPublicKey(file.getAbsolutePath());
            inputKeyComponent.setPathFileKey(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Load key thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File key không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public void verifySignatureFileMode() {
        fileMode = true;
        cardLayoutPanelInput.show(panelInput, "file");
        action.setText("Xác thực chữ ký file");
        dropAndDragComponent.removeFile();
    }

    public void verifySignatureTextMode() {
        fileMode = false;
        cardLayoutPanelInput.show(panelInput, "text");
        action.setText("Xác thực chữ ký văn bản");
        inputTextComponent.setTextJTextArea("");
    }

    @Override
    public void update(Observable o, Object arg) {
        var sizeParent = getParent().getSize();
        inputSignComponent.setCustomSize(new Dimension(sizeParent.width - 200, 70));
        panelInput.setPreferredSize(new Dimension(sizeParent.width - 200, sizeParent.height - V_GAP * 6 - 50 - 110 - 150 - 70));
        dropAndDragComponent.setCustomSize(new Dimension(sizeParent.width - 400, sizeParent.height - V_GAP * 6 - 50 - 110 - 150 - 70));
        inputTextComponent.setCustomSize(panelInput.getSize());
    }
}
