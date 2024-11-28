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
import com.lamnguyen.model.signature.ISign;
import com.lamnguyen.model.signature.impl.SignFileImpl;
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

public class SignPage extends JPanel implements Observer {
    private final Application application;
    private DropAndDragComponent dropAndDragComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectSignatureAlgorithmComponent selectAlgorithmComponent;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private PrivateKey privateKey;
    private OutputInputTextComponent resultComponent;
    private final int V_GAP = 20;
    private CardLayout cardLayoutPanelInput;
    private JPanel panelInput;
    private OutputInputTextComponent inputTextComponent;
    private boolean fileMode;
    private JButton action;


    public SignPage(Application application) {
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

        dropAndDragComponent = new DropAndDragComponent();
        panelInput.add(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)) {{
            setOpaque(false);
            add(dropAndDragComponent);
        }}, "file");

        inputTextComponent = new OutputInputTextComponent("Nhập văn bản");
        panelInput.add(inputTextComponent, "text");

        inputKeyComponent = new InputKeyComponent(this::loadFileKey);//100
        this.add(inputKeyComponent);
        sizeController.addObserver(inputKeyComponent);

        selectAlgorithmComponent = new SelectSignatureAlgorithmComponent(SignatureAlgorithmConfig.getInstance().getAlgorithmSignature()); //150
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        action = new JButton("Ký") {{
            setPreferredSize(new Dimension(500, 50));
            addActionListener(action -> {
                if (fileMode) signFile();
                else signText();
            });
        }};
        this.add(action); // 50

        resultComponent = new OutputInputTextComponent("Chữ kí file!") {{
            setEditable(false);
            clickToCopy(true);
        }};
        this.add(resultComponent);
    }


    private void signFile() {
        DialogProgressHelper.runProcess(process -> {
            var file = dropAndDragComponent.getPathFile();
            var alg = selectAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateFile(file, process) || !ValidationHelper.validateKey(privateKey, process))
                return;

            try {
                ISign signer = SignFileImpl.getInstance(alg, privateKey);
                setSignatureString(signer.signFile(file));
                JOptionPane.showMessageDialog(null, "Ký file thành công!");
            } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {
                process.dispose();
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException | SignatureException e) {
                JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            process.dispose();
        });
    }

    private void signText() {
        DialogProgressHelper.runProcess(process -> {
            var text = inputTextComponent.getText();
            var alg = selectAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateKey(privateKey, process) || !ValidationHelper.validateText(text, process))
                return;

            try {
                ISign signer = SignFileImpl.getInstance(alg, privateKey);
                setSignatureString(signer.signText(text));
                JOptionPane.showMessageDialog(null, "Ký file thành công!");
            } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {
                process.dispose();
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SignatureException e) {
                JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            process.dispose();
        });
    }

    public Void loadFileKey(File file) {
        if (file == null) return null;
        try {
            privateKey = IAsymmetrical.KeyFactory.readPrivateKey(file.getAbsolutePath());
            inputKeyComponent.setPathFileKey(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Load key thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File key không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    private void event() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control F"), "Ctrl_F");
        getActionMap().put("Ctrl_F", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignPage.this.signFileMode();
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control T"), "Ctrl_T");
        getActionMap().put("Ctrl_T", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignPage.this.signTextMode();
            }
        });
    }

    public void signFileMode() {
        fileMode = true;
        cardLayoutPanelInput.show(panelInput, "file");
        action.setText("Ký file");
        setSignatureString("");
        dropAndDragComponent.removeFile();
    }

    public void signTextMode() {
        fileMode = false;
        cardLayoutPanelInput.show(panelInput, "text");
        action.setText("Ký văn bản");
        setSignatureString("");
        inputTextComponent.setTextJTextArea("");
    }

    private void setSignatureString(String signature) {
        resultComponent.setTextJTextArea(signature);
    }

    @Override
    public void update(Observable o, Object arg) {
        var sizeParent = this.getParent().getSize();
        resultComponent.setCustomSize(new Dimension(sizeParent.width - 200, 70));
        panelInput.setPreferredSize(new Dimension(sizeParent.width - 200, sizeParent.height - V_GAP * 6 - 50 - 110 - 150 - resultComponent.getHeight()));
        dropAndDragComponent.setCustomSize(new Dimension(sizeParent.width - 400, sizeParent.height - V_GAP * 6 - 50 - 110 - 150 - resultComponent.getHeight()));
        inputTextComponent.setCustomSize(panelInput.getSize());
    }
}
