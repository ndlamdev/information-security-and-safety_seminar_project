/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:40 AM - 11/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.view;

import main.java.security.symmetrical.ISymmetrical;
import main.java.ui.fileEncrypt.component.selector.SelectKeyAlgorithmSymmetricalComponent;
import main.java.ui.fileEncrypt.component.key.KeySymmetricalGenerateComponent;
import main.java.ui.fileEncrypt.component.output.OutputComponent;
import main.java.ui.fileEncrypt.controller.SubjectSizeController;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class GenerateKeySymmetricalPage extends JPanel implements Observer {
    private KeySymmetricalGenerateComponent keyGenerateComponent;
    private SelectKeyAlgorithmSymmetricalComponent selectAlgorithmComponent;
    private OutputComponent outputComponent;
    private String keyBase64;
    private SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private JButton buttonCreate;
    private JPanel panelSpace;

    public GenerateKeySymmetricalPage() {
        this.init();
    }

    private void init() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));

        Function<SelectKeyAlgorithmSymmetricalComponent.AlgorithmKey, Void> onAlgorithmKeyChanged = algorithmKey -> {
            outputComponent.setFileName(algorithmKey.getName() + "_" + algorithmKey.getSize());
            keyBase64 = null;
            keyGenerateComponent.setKey("");
            return null;
        };

        keyGenerateComponent = new KeySymmetricalGenerateComponent();
        this.add(keyGenerateComponent);
        sizeController.addObserver(keyGenerateComponent);

        selectAlgorithmComponent = new SelectKeyAlgorithmSymmetricalComponent(onAlgorithmKeyChanged);
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        buttonCreate = new JButton("Tạo khóa!") {{
            addActionListener(actionEvent -> {
                var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
                var name = ISymmetrical.Algorithms.valueOf(algorithmKey.getName());
                SecretKey key = ISymmetrical.KeyFactory.generateKey(name, algorithmKey.getSize());
                keyBase64 = ISymmetrical.encodeKeyToBase64(key);
                keyGenerateComponent.setKey(keyBase64);
            });
        }};
        this.add(buttonCreate);

        panelSpace = new JPanel() {{
            setOpaque(false);
        }};
        this.add(panelSpace);

        var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
        outputComponent = new OutputComponent() {{
            setTextButtonAction("Xuất file");
            setFileName(algorithmKey.getName() + "_" + algorithmKey.getSize());
            setExtensionFile(".keys");
            setActionButtonAction(actionEvent -> saveKey());
        }};
        this.add(outputComponent);
        sizeController.addObserver(outputComponent);
    }

    private void saveKey() {
        if (keyBase64 == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng tạo khóa trước!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ISymmetrical.saveKey(selectAlgorithmComponent.getAlgorithmKey().getName(), keyBase64, outputComponent.getFullPath());
            JOptionPane.showMessageDialog(null, "Thành công!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        var parentSize = getParent().getWidth();
        buttonCreate.setPreferredSize(new Dimension(parentSize - 500, 50));
        panelSpace.setPreferredSize(new Dimension(parentSize - 200, 200));
    }
}
