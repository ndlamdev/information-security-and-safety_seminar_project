/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:40 AM - 11/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.config.KeyConfig;
import com.lamnguyen.helper.SettingHelper;
import com.lamnguyen.security.asymmetrical.AsymmetricalKey;
import com.lamnguyen.security.asymmetrical.IAsymmetrical;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.key.KeyAsymmetricalGenerateComponent;
import com.lamnguyen.ui.component.output.OutputComponent;
import com.lamnguyen.ui.component.selector.SelectAlgorithmGenerateKeyComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class GenerateKeyAsymmetricalPage extends JPanel implements Observer {
    private AsymmetricalKey key;
    private KeyAsymmetricalGenerateComponent keyGenerateComponent;
    private SelectAlgorithmGenerateKeyComponent selectAlgorithmComponent;
    private OutputComponent outputComponent;
    private String privateKeyBase64, publicKeyBase64;
    private SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private JButton buttonCreate;
    private JPanel panelSpace;
    private Application application;

    public GenerateKeyAsymmetricalPage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));

        Function<SelectAlgorithmGenerateKeyComponent.AlgorithmKey, Void> onAlgorithmKeyChanged = algorithmKey -> {
            outputComponent.setFileName(algorithmKey.getName() + "_" + algorithmKey.getSize());
            key = null;
            privateKeyBase64 = null;
            publicKeyBase64 = null;
            keyGenerateComponent.setPrivateKey("");
            keyGenerateComponent.setPublicKey("");
            return null;
        };

        keyGenerateComponent = new KeyAsymmetricalGenerateComponent();
        this.add(keyGenerateComponent);
        sizeController.addObserver(keyGenerateComponent);

        selectAlgorithmComponent = new SelectAlgorithmGenerateKeyComponent(onAlgorithmKeyChanged, KeyConfig.getInstance().getMapAlgorithmAsymmetrical());
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        buttonCreate = new JButton("Tạo khóa!") {{
            addActionListener(actionEvent -> generateKey());
        }};
        this.add(buttonCreate);

        panelSpace = new JPanel() {{
            setOpaque(false);
        }};
        this.add(panelSpace);

        var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
        outputComponent = new OutputComponent() {{
            setPathFolder(SettingHelper.getInstance().getWorkSpace() + "/key");
            setTextButtonAction("Xuất file");
            setFileName(algorithmKey.getName() + "_" + algorithmKey.getSize());
            setExtensionFile(".keys");
            setActionButtonAction(actionEvent -> saveKey());
        }};
        this.add(outputComponent);
        sizeController.addObserver(outputComponent);
    }

    private void saveKey() {
        if (privateKeyBase64 == null || publicKeyBase64 == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng tạo khóa trước!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            new File(outputComponent.getFolderDest()).mkdirs();
            IAsymmetrical.saveKey(selectAlgorithmComponent.getAlgorithmKey().getName(), key, outputComponent.getFullPath());
            if (outputComponent.getFullPath().startsWith(SettingHelper.getInstance().getWorkSpace()))
                application.reloadWorkSpace();
            JOptionPane.showMessageDialog(null, "Thành công!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lưu file tất bại!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        var parentSize = getParent().getWidth();
        buttonCreate.setPreferredSize(new Dimension(parentSize - 500, 50));
        panelSpace.setPreferredSize(new Dimension(parentSize - 200, 110));
    }

    private void generateKey() {
        var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
        var name = IAsymmetrical.KeyFactory.Algorithms.valueOf(algorithmKey.getName());
        key = IAsymmetrical.KeyFactory.generateKey(name, algorithmKey.getSize());
        if (key == null) return;
        privateKeyBase64 = IAsymmetrical.encodeKeyToBase64(key.privateKey());
        publicKeyBase64 = IAsymmetrical.encodeKeyToBase64(key.publicKey());
        keyGenerateComponent.setPrivateKey(privateKeyBase64);
        keyGenerateComponent.setPublicKey(publicKeyBase64);
    }
}
