/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.view;

import main.java.security.symmetrical.ISymmetrical;
import main.java.ui.fileEncrypt.component.selector.SelectCipherAlgorithmSymmetricalComponent;
import main.java.ui.fileEncrypt.component.dropAndDrag.DropAndDragComponent;
import main.java.ui.fileEncrypt.component.key.InputKeyComponent;
import main.java.ui.fileEncrypt.component.output.OutputComponent;
import main.java.ui.fileEncrypt.controller.SubjectSizeController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;

public class CipherSymmetricalPage extends JPanel {
    private OutputComponent outputComponent;
    private DropAndDragComponent dropAndDragComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectCipherAlgorithmSymmetricalComponent selectAlgorithmComponent;
    private boolean encrypt = true;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();

    //1300
    public CipherSymmetricalPage() {
        this.init();
    }

    private void init() {
        this.setOpaque(false);

        Function<String, Void> onFileChanged = pathFile -> {
            setFileNameOut(pathFile, selectAlgorithmComponent.getAlgorithm());
            return null;
        };

        Function<SelectCipherAlgorithmSymmetricalComponent.Algorithm, Void> onAlgorithmChanged = algorithm -> {
            File file = dropAndDragComponent.getFile();
            if (file == null) return null;

            setFileNameOut(file.getAbsolutePath(), algorithm);
            return null;
        };

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));

        dropAndDragComponent = new DropAndDragComponent(onFileChanged);
        sizeController.addObserver(dropAndDragComponent);
        this.add(dropAndDragComponent);

        inputKeyComponent = new InputKeyComponent();
        this.add(inputKeyComponent);
        sizeController.addObserver(inputKeyComponent);

        selectAlgorithmComponent = new SelectCipherAlgorithmSymmetricalComponent(onAlgorithmChanged);
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        outputComponent = new OutputComponent();
        this.add(outputComponent);
        sizeController.addObserver(outputComponent);

        encryptMode();
    }

    public void encryptMode() {
        outputComponent.setTextButtonAction("Mã hóa!");
        encrypt = true;
        outputComponent.setActionButtonAction(actionEvent -> {
            var file = dropAndDragComponent.getPathFile();
            var alg = selectAlgorithmComponent.getAlgorithm();
            var key = inputKeyComponent.getKey();
            if (!validation(file, key)) return;

            try {
                var cipher = ISymmetrical.Factory.createEncrypt(alg.algorithm(), alg.mode(), alg.padding(), key);
                cipher.encryptFile(file, outputComponent.getFullPath(), false);
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (IllegalBlockSizeException | IOException | BadPaddingException | NoSuchPaddingException |
                     NoSuchAlgorithmException | InvalidKeyException e) {
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void decryptMode() {
        encrypt = false;
        outputComponent.setTextButtonAction("Giải mã!");
        outputComponent.setActionButtonAction(actionEvent -> {
            var file = dropAndDragComponent.getPathFile();
            var alg = selectAlgorithmComponent.getAlgorithm();
            var key = inputKeyComponent.getKey();
            if (!validation(file, key)) return;

            try {
                var cipher = ISymmetrical.Factory.createDecrypt(alg.algorithm(), alg.mode(), alg.padding(), key);
                cipher.decryptFile(file, outputComponent.getFullPath(), 0);
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (IllegalBlockSizeException | IOException | BadPaddingException | NoSuchPaddingException |
                     NoSuchAlgorithmException | InvalidKeyException e) {
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean validation(String file, SecretKey key) {
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

    private void setFileNameOut(String pathFile, SelectCipherAlgorithmSymmetricalComponent.Algorithm algorithm) {
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
