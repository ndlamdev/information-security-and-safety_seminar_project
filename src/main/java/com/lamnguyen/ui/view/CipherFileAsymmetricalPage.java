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
import com.lamnguyen.model.asymmetrical.IAsymmetrical;
import com.lamnguyen.model.asymmetrical.decrypt.AASymmetricalDecrypt;
import com.lamnguyen.model.symmetrical.ISymmetrical;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.dropAndDrag.DropAndDragComponent;
import com.lamnguyen.ui.component.key.InputKeyComponent;
import com.lamnguyen.ui.component.output.OutputComponent;
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
import java.util.function.Function;

public class CipherFileAsymmetricalPage extends JPanel implements Observer {
    private final Application application;
    private OutputComponent outputComponent;
    private DropAndDragComponent dropAndDragComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectCipherAlgorithmComponent selectCipherFileAlgorithmComponent, selectCipherKeyAlgorithmComponent;
    private boolean encrypt = true;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private final int V_GAP = 20;
    private int heightDrop;

    public CipherFileAsymmetricalPage(Application application) {
        this.application = application;
        this.init();
        this.event();
    }

    private void init() {
        this.setOpaque(false);

        Function<String, Void> onFileChanged = pathFile -> {
            setFileNameOut(pathFile, selectCipherFileAlgorithmComponent.getAlgorithm());
            return null;
        };

        Function<SelectCipherAlgorithmComponent.Algorithm, Void> onAlgorithmChanged = algorithm -> {
            File file = dropAndDragComponent.getFile();
            if (file == null) return null;

            setFileNameOut(file.getAbsolutePath(), algorithm);
            return null;
        };

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, V_GAP));

        dropAndDragComponent = new DropAndDragComponent(onFileChanged);
        this.add(dropAndDragComponent);

        inputKeyComponent = new InputKeyComponent(this::loadFileKey); // 110
        this.add(inputKeyComponent);
        sizeController.addObserver(inputKeyComponent);

        selectCipherFileAlgorithmComponent = new SelectCipherAlgorithmComponent(CipherAlgorithmConfig.getInstance().getAlgorithmSymmetrical(), onAlgorithmChanged) {{
            setTitle("Lựa chọn thuật toán " + (encrypt ? "mã hóa" : "giải mã") + " file!");
        }};
        this.add(selectCipherFileAlgorithmComponent);
        selectCipherFileAlgorithmComponent.setHeightItemComponent(new SelectCipherAlgorithmComponent.HeightItemComponent(20, 30, 15));
        sizeController.addObserver(selectCipherFileAlgorithmComponent);

        selectCipherKeyAlgorithmComponent = new SelectCipherAlgorithmComponent(CipherAlgorithmConfig.getInstance().getAlgorithmAsymmetrical(), onAlgorithmChanged) {{
            setTitle("Lựa chọn thuật toán " + (encrypt ? "mã hóa" : "giải mã") + " khóa!!");
        }};
        this.add(selectCipherKeyAlgorithmComponent);
        sizeController.addObserver(selectCipherKeyAlgorithmComponent);

        outputComponent = new OutputComponent(); //110
        sizeController.addObserver(outputComponent);
        this.add(outputComponent);
    }

    private void event() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control D"), "Ctrl_D");
        getActionMap().put("Ctrl_D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CipherFileAsymmetricalPage.this.decryptMode();
            }
        });

        // Add Key Binding for Ctrl + E
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control E"), "Ctrl_E");
        getActionMap().put("Ctrl_E", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CipherFileAsymmetricalPage.this.encryptMode();
            }
        });
    }

    public Void loadFileKey(File file) {
        if (file == null) return null;
        try {
            if (encrypt) publicKey = IAsymmetrical.KeyFactory.readPublicKey(file.getAbsolutePath());
            else privateKey = IAsymmetrical.KeyFactory.readPrivateKey(file.getAbsolutePath());
            inputKeyComponent.setPathFileKey(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Load key thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File key không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public void encryptMode() {
        encrypt = true;
        outputComponent.setTextButtonAction("Mã hóa!");
        outputComponent.setActionButtonAction(actionEvent -> encryptFile());
        selectCipherFileAlgorithmComponent.setVisible(true);
        selectCipherKeyAlgorithmComponent.setHeightItemComponent(new SelectCipherAlgorithmComponent.HeightItemComponent(20, 30, 15));
        var width = dropAndDragComponent.getSize().width;
        dropAndDragComponent.setCustomSize(new Dimension(width, heightDrop - selectCipherKeyAlgorithmComponent.getHeight() - selectCipherKeyAlgorithmComponent.getHeight() - V_GAP * 6));
        dropAndDragComponent.removeFile();
        publicKey = null;
        inputKeyComponent.setPathFileKey("");
        inputKeyComponent.setToolTipText("Nhập khóa công khai!");
    }

    public void decryptMode() {
        encrypt = false;
        outputComponent.setTextButtonAction("Giải mã!");
        outputComponent.setActionButtonAction(actionEvent -> decryptFile());
        selectCipherFileAlgorithmComponent.setVisible(false);
        selectCipherKeyAlgorithmComponent.setHeightItemComponent(new SelectCipherAlgorithmComponent.HeightItemComponent(20, 50, 20));
        var width = dropAndDragComponent.getSize().width;
        dropAndDragComponent.setCustomSize(new Dimension(width, heightDrop - selectCipherKeyAlgorithmComponent.getHeight() - V_GAP * 5));
        dropAndDragComponent.removeFile();
        privateKey = null;
        inputKeyComponent.setPathFileKey("");
        inputKeyComponent.setToolTipText("Nhập khóa riêng tư!");
    }

    private void encryptFile() {
        DialogProgressHelper.runProcess(process -> {
            var file = dropAndDragComponent.getPathFile();
            var algEncryptFile = selectCipherFileAlgorithmComponent.getAlgorithm();
            var algEncryptKey = selectCipherKeyAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateAlgorithm(algEncryptKey, process) || !ValidationHelper.validateAlgorithm(algEncryptFile, process) || !ValidationHelper.validateKey(publicKey, process) || !ValidationHelper.validateFile(file, process))
                return;
            var cipher = IAsymmetrical.Factory.createEncrypt(algEncryptKey.algorithm());
            try {
                cipher.loadKey(publicKey);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     NoSuchProviderException e) {
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
                process.dispose();
                return;
            }
            if (!DialogOverFileHelper.overwriteFile(outputComponent.getFullPath(), process)) return;
            try {
                cipher.encryptFile(ISymmetrical.Algorithms.valueOf(algEncryptFile.algorithm()), algEncryptFile.mode(), algEncryptFile.padding(), file, outputComponent.getFullPath());
                if (outputComponent.getFullPath().startsWith(SettingHelper.getInstance().getWorkSpace()))
                    application.reloadWorkSpaceSync();
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (IOException | IllegalBlockSizeException | BadPaddingException |
                     InvalidAlgorithmParameterException | NoSuchPaddingException | NoSuchAlgorithmException |
                     InvalidKeyException | NoSuchProviderException e) {
                e.printStackTrace(System.out);
                JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            process.dispose();
        });
    }

    private void decryptFile() {
        DialogProgressHelper.runProcess(process -> {
            var file = dropAndDragComponent.getPathFile();
            var algDecryptFile = selectCipherKeyAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateAlgorithm(algDecryptFile, process) || !ValidationHelper.validateFile(file, process) || !ValidationHelper.validateKey(privateKey, process))
                return;
            var cipher = IAsymmetrical.Factory.createDecrypt(algDecryptFile.algorithm());
            try {
                cipher.loadKey(privateKey);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     NoSuchProviderException e) {
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
                process.dispose();
                return;
            }
            if (!DialogOverFileHelper.overwriteFile(outputComponent.getFullPath(), process)) return;
            try {
                cipher.decryptFile(file, outputComponent.getFullPath());
                if (outputComponent.getFullPath().startsWith(SettingHelper.getInstance().getWorkSpace()))
                    application.reloadWorkSpaceSync();
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (IOException | IllegalBlockSizeException | BadPaddingException |
                     InvalidAlgorithmParameterException | NoSuchPaddingException | NoSuchAlgorithmException |
                     InvalidKeyException | NoSuchProviderException | AASymmetricalDecrypt.HeaderException e) {
                e.printStackTrace(System.out);
                JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            process.dispose();
        });
    }

    private void setFileNameOut(String pathFile, SelectCipherAlgorithmComponent.Algorithm algorithm) {
        if (pathFile == null) {
            outputComponent.setPathFolder("");
            outputComponent.setFileName("");
            outputComponent.setExtensionFile("");

            return;
        }

        var indexOf = pathFile.lastIndexOf(File.separator);
        String pathFolder = indexOf == -1 ? "" : pathFile.substring(0, indexOf);
        String fullFileName = pathFile.substring(pathFolder.length() + 1);
        String nameFile = fullFileName.substring(0, fullFileName.lastIndexOf("."));
        String extensionFile = fullFileName.substring(nameFile.length());

        outputComponent.setPathFolder(pathFolder);
        var nameAlg = algorithm.algorithm() + (algorithm.mode() == null ? "" : "-" + algorithm.mode()) + (algorithm.padding() == null ? "" : "-" + algorithm.padding());
        outputComponent.setFileName(nameFile + (encrypt ? "_encrypt" : "_decrypt") + "_by_" + nameAlg);
        outputComponent.setExtensionFile(extensionFile);
    }

    @Override
    public void update(Observable o, Object arg) {
        var sizeParent = this.getParent().getSize();
        heightDrop = sizeParent.height - 110 - 110;
        dropAndDragComponent.setPreferredSize(new Dimension(sizeParent.width - 400, heightDrop - selectCipherKeyAlgorithmComponent.getHeight() - selectCipherFileAlgorithmComponent.getHeight() - V_GAP * 6));
        dropAndDragComponent.setSize(dropAndDragComponent.getPreferredSize());
    }
}
