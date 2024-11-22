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
import com.lamnguyen.model.asymmetrical.AsymmetricalKey;
import com.lamnguyen.model.asymmetrical.IAsymmetrical;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.output.KeyAsymmetricalGenerateComponent;
import com.lamnguyen.ui.component.output.OutputComponent;
import com.lamnguyen.ui.component.selector.SelectAlgorithmGenerateKeyComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;
import com.lamnguyen.helper.DialogProgressHelper;

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
    private SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private JButton buttonCreate;
    private JPanel panelSpace;
    private Application application;
    private final int V_GAP = 20;

    public GenerateKeyAsymmetricalPage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, V_GAP));

        Function<SelectAlgorithmGenerateKeyComponent.AlgorithmKey, Void> onAlgorithmKeyChanged = algorithmKey -> {
            outputComponent.setFileName(algorithmKey.getName() + "_" + algorithmKey.getSize());
            key = null;
            keyGenerateComponent.setPrivateKey("");
            keyGenerateComponent.setPublicKey("");
            return null;
        };

        keyGenerateComponent = new KeyAsymmetricalGenerateComponent(); //240
        this.add(keyGenerateComponent);
        sizeController.addObserver(keyGenerateComponent);

        selectAlgorithmComponent = new SelectAlgorithmGenerateKeyComponent(onAlgorithmKeyChanged, KeyConfig.getInstance().getMapAlgorithmAsymmetrical()); //150
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        buttonCreate = new JButton("Tạo khóa!") {{
            addActionListener(actionEvent -> generateKey());
        }}; //50
        this.add(buttonCreate);

        panelSpace = new JPanel() {{
            setOpaque(false);
        }};
        this.add(panelSpace);

        var algorithmKey = selectAlgorithmComponent.getAlgorithmKey(); //110
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
        DialogProgressHelper.runProcess(process -> {
            if (key == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng tạo khóa trước!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                new File(outputComponent.getFolderDest()).mkdirs();
                IAsymmetrical.saveKey(selectAlgorithmComponent.getAlgorithmKey().getName(), key, outputComponent.getFullPath());
                if (outputComponent.getFullPath().startsWith(SettingHelper.getInstance().getWorkSpace()))
                    application.reloadWorkSpace();
                process.dispose();
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (IOException e) {
                process.dispose();
                JOptionPane.showMessageDialog(null, "Lưu file tất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        var parentSize = getParent().getWidth();
        buttonCreate.setPreferredSize(new Dimension(parentSize - 500, 50));
        var sizeSpace = this.getHeight() - V_GAP * 6 - 110 - 130 - 50 - 230;
        panelSpace.setPreferredSize(new Dimension(parentSize - 200, sizeSpace));
    }

    private void generateKey() {
        DialogProgressHelper.runProcess(process -> {
            var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
            var name = IAsymmetrical.KeyFactory.Algorithms.valueOf(algorithmKey.getName());
            key = IAsymmetrical.KeyFactory.generateKey(name, algorithmKey.getSize());
            if (key == null) return;
            keyGenerateComponent.setPrivateKey(IAsymmetrical.encodeKeyToBase64(key.privateKey()));
            keyGenerateComponent.setPublicKey(IAsymmetrical.encodeKeyToBase64(key.publicKey()));
            process.dispose();
        });
    }
}
