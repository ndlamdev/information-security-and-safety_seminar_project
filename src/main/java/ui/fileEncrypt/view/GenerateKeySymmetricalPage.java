/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:40 AM - 11/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.view;

import main.java.security.symmetrical.ISymmetrical;
import main.java.security.symmetrical.encrypt.ISymmetricalEncrypt;
import main.java.ui.fileEncrypt.component.algorithm.SelectAlgorithmCipherSymmetricalComponent;
import main.java.ui.fileEncrypt.component.key.KeySymmetricalGenerateComponent;
import main.java.ui.fileEncrypt.component.output.OutputComponent;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;

public class GenerateKeySymmetricalPage extends JPanel {
    private KeySymmetricalGenerateComponent keyGenerateComponent;
    private SelectAlgorithmCipherSymmetricalComponent selectAlgorithmComponent;
    private OutputComponent outputComponent;
    private String keyBase64;

    public GenerateKeySymmetricalPage() {
        this.init();
    }

    private void init() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));

        Function<SelectAlgorithmCipherSymmetricalComponent.AlgorithmKey, Void> onAlgorithmKeyChanged = algorithmKey -> {
            outputComponent.setFileName(algorithmKey.getName() + "_" + algorithmKey.getSize());
            keyBase64 = null;
            keyGenerateComponent.setKey("");
            return null;
        };

        keyGenerateComponent = new KeySymmetricalGenerateComponent();
        this.add(keyGenerateComponent);

        selectAlgorithmComponent = new SelectAlgorithmCipherSymmetricalComponent(onAlgorithmKeyChanged);
        this.add(selectAlgorithmComponent);

        this.add(new JButton("Tạo khóa!") {{
            setPreferredSize(new Dimension(1000, 50));
            addActionListener(actionEvent -> {
                var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
                var name = ISymmetrical.Algorithms.valueOf(algorithmKey.getName());
                try {
                    ISymmetricalEncrypt encrypt = ISymmetrical.Factory.createEncrypt(name, algorithmKey.getSize());
                    keyBase64 = ISymmetrical.encodeKeyToBase64(encrypt.getKey());
                    keyGenerateComponent.setKey(keyBase64);
                } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
                    throw new RuntimeException(e);
                }
            });
        }});

        this.add(new JPanel() {{
            setPreferredSize(new Dimension(1000, 200));
        }});

        var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
        outputComponent = new OutputComponent() {{
            setTextButtonAction("Xuất file");
            setFileName(algorithmKey.getName() + "_" + algorithmKey.getSize());
            setExtensionFile(".keys");
            setActionButtonAction(actionEvent -> saveKey());
        }};
        this.add(outputComponent);
    }

    private void saveKey() {
        if (keyBase64 == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng tạo khóa trước!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputComponent.getFullPath()), 1024 * 10));
            outputStream.writeUTF(selectAlgorithmComponent.getAlgorithmKey().getName());
            outputStream.writeUTF(keyBase64);
            outputStream.close();
            JOptionPane.showMessageDialog(null, "Thành công!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
