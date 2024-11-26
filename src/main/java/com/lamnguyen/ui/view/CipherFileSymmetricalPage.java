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
import com.lamnguyen.model.symmetrical.ISymmetrical;
import com.lamnguyen.model.symmetrical.SymmetricalKey;
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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class CipherFileSymmetricalPage extends JPanel implements Observer {
    private final Application application;
    private OutputComponent outputComponent;
    private DropAndDragComponent dropAndDragComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectCipherAlgorithmComponent selectAlgorithmComponent;
    private boolean encrypt = true;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private SymmetricalKey key;
    private final int V_GAP = 20;

    public CipherFileSymmetricalPage(Application application) {
        this.application = application;
        this.init();
        this.event();
    }

    private void init() {
        this.setOpaque(false);

        Function<String, Void> onFileChanged = pathFile -> {
            setFileNameOut(pathFile, selectAlgorithmComponent.getAlgorithm());
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

        selectAlgorithmComponent = new SelectCipherAlgorithmComponent(CipherAlgorithmConfig.getInstance().getAlgorithmSymmetrical(), onAlgorithmChanged); // 160
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        outputComponent = new OutputComponent(); //110
        sizeController.addObserver(outputComponent);
        this.add(outputComponent);

        encryptMode();
    }

    private void event() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control D"), "Ctrl_D");
        getActionMap().put("Ctrl_D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CipherFileSymmetricalPage.this.decryptMode();
            }
        });

        // Add Key Binding for Ctrl + E
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control E"), "Ctrl_E");
        getActionMap().put("Ctrl_E", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CipherFileSymmetricalPage.this.encryptMode();
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

    public void encryptMode() {
        encrypt = true;
        outputComponent.setTextButtonAction("Mã hóa!");
        outputComponent.setActionButtonAction(actionEvent -> encryptFile());
    }

    public void decryptMode() {
        encrypt = false;
        outputComponent.setTextButtonAction("Giải mã!");
        outputComponent.setActionButtonAction(actionEvent -> decryptFile());
    }

    private void encryptFile() {
        DialogProgressHelper.runProcess(process -> {
            var file = dropAndDragComponent.getPathFile();
            var alg = selectAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateAlgorithm(alg, process) || !ValidationHelper.validateKey(key, process) || !ValidationHelper.validateFile(file, process))
                return;

            try {
                var cipher = ISymmetrical.Factory.createEncrypt(alg.algorithm(), alg.mode(), alg.padding(), key);
                if (!DialogOverFileHelper.overwriteFile(outputComponent.getFullPath(), process)) return;
                cipher.encryptFile(file, outputComponent.getFullPath(), false);
                if (outputComponent.getFullPath().startsWith(SettingHelper.getInstance().getWorkSpace()))
                    application.reloadWorkSpaceAsync();
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (NoSuchProviderException | InvalidKeyException | NoSuchAlgorithmException |
                     IllegalBlockSizeException | IOException |
                     BadPaddingException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
                e.printStackTrace(System.out);
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            process.dispose();
        });
    }

    private void decryptFile() {
        DialogProgressHelper.runProcess(process -> {
            var file = dropAndDragComponent.getPathFile();
            var alg = selectAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateAlgorithm(alg, process) || !ValidationHelper.validateKey(key, process) || !ValidationHelper.validateFile(file, process))
                return;

            try {
                var cipher = ISymmetrical.Factory.createDecrypt(alg.algorithm(), alg.mode(), alg.padding(), key);
                cipher.decryptFile(file, outputComponent.getFullPath(), 0);
                if (outputComponent.getFullPath().startsWith(SettingHelper.getInstance().getWorkSpace()))
                    application.reloadWorkSpaceSync();
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | IllegalBlockSizeException |
                     IOException | BadPaddingException | NoSuchPaddingException | InvalidKeyException |
                     NoSuchProviderException e) {
                e.printStackTrace(System.out);
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
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
        dropAndDragComponent.setPreferredSize(new Dimension(sizeParent.width - 400, sizeParent.height - selectAlgorithmComponent.getHeight() - 110 - 110 - V_GAP * 5));
        dropAndDragComponent.setSize(dropAndDragComponent.getPreferredSize());
    }
}
