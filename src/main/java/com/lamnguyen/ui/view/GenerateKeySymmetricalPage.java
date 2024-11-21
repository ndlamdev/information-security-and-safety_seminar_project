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
import com.lamnguyen.security.symmetrical.ISymmetrical;
import com.lamnguyen.security.symmetrical.encrypt.ISymmetricalEncrypt;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.key.KeySymmetricalGenerateComponent;
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

public class GenerateKeySymmetricalPage extends JPanel implements Observer {
    private KeySymmetricalGenerateComponent keyGenerateComponent;
    private SelectAlgorithmGenerateKeyComponent selectAlgorithmComponent;
    private OutputComponent outputComponent;
    private SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private JButton buttonCreate;
    private JPanel panelSpace;
    private Application application;
    private ISymmetricalEncrypt encrypt;
    private final int vGap = 20;

    public GenerateKeySymmetricalPage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, vGap));

        Function<SelectAlgorithmGenerateKeyComponent.AlgorithmKey, Void> onAlgorithmKeyChanged = algorithmKey -> {
            outputComponent.setFileName(algorithmKey.getName() + "_" + algorithmKey.getSize());
            encrypt = null;
            keyGenerateComponent.setKey("");
            return null;
        };

        keyGenerateComponent = new KeySymmetricalGenerateComponent(); // 110
        this.add(keyGenerateComponent);
        sizeController.addObserver(keyGenerateComponent);

        selectAlgorithmComponent = new SelectAlgorithmGenerateKeyComponent(onAlgorithmKeyChanged, KeyConfig.getInstance().getMapAlgorithmSymmetrical()); // 150
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        buttonCreate = new JButton("Tạo khóa!") {{
            addActionListener(actionEvent -> {
                var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
                var name = ISymmetrical.Algorithms.valueOf(algorithmKey.getName());
                try {
                    encrypt = ISymmetrical.Factory.createEncrypt(name, null, null, algorithmKey.getSize());
                    keyGenerateComponent.setKey(ISymmetrical.encodeKeyToBase64(encrypt.getKey()));
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            });
        }}; //50
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
        }}; // 110
        this.add(outputComponent, BorderLayout.SOUTH);
        sizeController.addObserver(outputComponent);
    }

    private void saveKey() {
        if (encrypt == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng tạo khóa trước!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            new File(outputComponent.getFolderDest()).mkdirs();
            encrypt.saveKey(outputComponent.getFullPath());
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
        buttonCreate.setPreferredSize(new Dimension(parentSize - 500, 50)); //50
        var sizeSpace = this.getHeight() - vGap * 6 - 110 * 2 - 50 - 150;
        panelSpace.setPreferredSize(new Dimension(parentSize - 200, sizeSpace));
    }
}
