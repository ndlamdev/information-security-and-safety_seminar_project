/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.view;

import main.java.ui.fileEncrypt.component.algorithm.SelectAlgorithmComponent;
import main.java.ui.fileEncrypt.component.dropAndDrag.DropAndDragComponent;
import main.java.ui.fileEncrypt.component.key.InputKeyComponent;
import main.java.ui.fileEncrypt.component.output.OutputComponent;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class MainPage extends JPanel {
    private Function<String, Void> onFileChanged;
    private OutputComponent outputComponent;

    public MainPage() {
        this.init();
    }

    private void init() {
        this.onFileChanged = s -> {
            if (s == null) {
                outputComponent.setPathFolder("");
                outputComponent.setFileName("");
                outputComponent.setExtensionFile("");

                return null;
            }

            String pathFolder = s.substring(0, s.lastIndexOf("/"));
            String fullFileName = s.substring(pathFolder.length() + 1);
            String nameFile = fullFileName.substring(0, fullFileName.lastIndexOf("."));
            String extensionFile = fullFileName.substring(nameFile.length());

            outputComponent.setPathFolder(pathFolder);
            outputComponent.setFileName(nameFile + "_encrypt");
            outputComponent.setExtensionFile(extensionFile);
            return null;
        };

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
        this.add(new DropAndDragComponent(onFileChanged));
        this.add(new InputKeyComponent());
        this.add(new SelectAlgorithmComponent());
        outputComponent = new OutputComponent();
        this.add(outputComponent);
    }

    public void encryptMode() {
        outputComponent.setTextButtonAction("Mã hóa!");
    }

    public void decryptMode() {
        outputComponent.setTextButtonAction("Giải mã!");
    }
}
