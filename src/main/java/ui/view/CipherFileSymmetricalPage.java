/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.view;

import main.java.config.CipherAlgorithmConfig;
import main.java.helper.SettingHelper;
import main.java.security.symmetrical.ISymmetrical;
import main.java.security.symmetrical.SymmetricalKey;
import main.java.ui.Application;
import main.java.ui.component.selector.SelectCipherAlgorithmComponent;
import main.java.ui.component.dropAndDrag.DropAndDragComponent;
import main.java.ui.component.key.InputKeyComponent;
import main.java.ui.component.output.OutputComponent;
import main.java.ui.controller.SubjectSizeController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;

public class CipherFileSymmetricalPage extends JPanel {
    private final Application application;
    private OutputComponent outputComponent;
    private DropAndDragComponent dropAndDragComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectCipherAlgorithmComponent selectAlgorithmComponent;
    private boolean encrypt = true;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private SymmetricalKey key;


    //1300
    public CipherFileSymmetricalPage(Application application) {
        this.application = application;
        this.init();
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

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));

        dropAndDragComponent = new DropAndDragComponent(onFileChanged);
        sizeController.addObserver(dropAndDragComponent);
        this.add(dropAndDragComponent);

        inputKeyComponent = new InputKeyComponent(this::loadFileKey);
        this.add(inputKeyComponent);
        sizeController.addObserver(inputKeyComponent);

        selectAlgorithmComponent = new SelectCipherAlgorithmComponent(CipherAlgorithmConfig.getInstance().getAlgorithmSymmetrical(), onAlgorithmChanged);
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        outputComponent = new OutputComponent();
        this.add(outputComponent);
        sizeController.addObserver(outputComponent);

        encryptMode();
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
        outputComponent.setTextButtonAction("Mã hóa!");
        encrypt = true;
        outputComponent.setActionButtonAction(actionEvent -> encryptFile());
    }

    public void decryptMode() {
        encrypt = false;
        outputComponent.setTextButtonAction("Giải mã!");
        outputComponent.setActionButtonAction(actionEvent -> decryptFile());
    }

    private void encryptFile() {
        var file = dropAndDragComponent.getPathFile();
        var alg = selectAlgorithmComponent.getAlgorithm();
        if (!validate(file, key)) return;

        try {
            var cipher = ISymmetrical.Factory.createEncrypt(alg.algorithm(), alg.mode(), alg.padding(), key);
            cipher.encryptFile(file, outputComponent.getFullPath(), false);
            if (outputComponent.getFullPath().startsWith(SettingHelper.getInstance().getWorkSpace()))
                application.reloadWorkSpace();
            JOptionPane.showMessageDialog(null, "Thành công!");
        } catch (IllegalBlockSizeException | IOException | BadPaddingException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchAlgorithmException e) {
            if (alg.padding() == null)
                JOptionPane.showMessageDialog(null, "Vui lòng chọn padding nếu đã chọn mode!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (alg.mode() == null)
                JOptionPane.showMessageDialog(null, "Vui lòng chọn mode nếu đã chọn padding!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private void decryptFile() {
        var file = dropAndDragComponent.getPathFile();
        var alg = selectAlgorithmComponent.getAlgorithm();
        if (!validate(file, key)) return;

        try {
            var cipher = ISymmetrical.Factory.createDecrypt(alg.algorithm(), alg.mode(), alg.padding(), key);
            cipher.decryptFile(file, outputComponent.getFullPath(), 0);
            if (outputComponent.getFullPath().startsWith(SettingHelper.getInstance().getWorkSpace()))
                application.reloadWorkSpace();
            JOptionPane.showMessageDialog(null, "Thành công!");
        } catch (IllegalBlockSizeException | IOException | BadPaddingException | NoSuchPaddingException |
                 InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchAlgorithmException e) {
            if (alg.padding() == null)
                JOptionPane.showMessageDialog(null, "Vui lòng chọn padding nếu đã chọn mode!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (alg.mode() == null)
                JOptionPane.showMessageDialog(null, "Vui lòng chọn mode nếu đã chọn padding!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
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

    private void setFileNameOut(String pathFile, SelectCipherAlgorithmComponent.Algorithm algorithm) {
        if (pathFile == null) {
            outputComponent.setPathFolder("");
            outputComponent.setFileName("");
            outputComponent.setExtensionFile("");

            return;
        }

        String pathFolder = pathFile.substring(0, pathFile.lastIndexOf("/"));
        String fullFileName = pathFile.substring(pathFolder.length() + 1);
        String nameFile = fullFileName.substring(0, fullFileName.lastIndexOf("."));
        String extensionFile = fullFileName.substring(nameFile.length());

        outputComponent.setPathFolder(pathFolder);
        var nameAlg = algorithm.algorithm().name() + (algorithm.mode() == null ? "" : "-" + algorithm.mode()) + (algorithm.padding() == null ? "" : "-" + algorithm.padding());
        outputComponent.setFileName(nameFile + (encrypt ? "_encrypt" : "_decrypt") + "_by_" + nameAlg);
        outputComponent.setExtensionFile(extensionFile);
    }
}
