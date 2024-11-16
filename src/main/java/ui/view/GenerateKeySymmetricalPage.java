/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:40 AM - 11/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.view;

import main.java.config.KeyConfig;
import main.java.helper.SettingHelper;
import main.java.security.symmetrical.ISymmetrical;
import main.java.security.symmetrical.encrypt.ISymmetricalEncrypt;
import main.java.ui.Application;
import main.java.ui.component.key.KeySymmetricalGenerateComponent;
import main.java.ui.component.output.OutputComponent;
import main.java.ui.component.selector.SelectAlgorithmGenerateKeyComponent;
import main.java.ui.controller.SubjectSizeController;

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

    public GenerateKeySymmetricalPage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));

        Function<SelectAlgorithmGenerateKeyComponent.AlgorithmKey, Void> onAlgorithmKeyChanged = algorithmKey -> {
            outputComponent.setFileName(algorithmKey.getName() + "_" + algorithmKey.getSize());
            encrypt = null;
            keyGenerateComponent.setKey("");
            return null;
        };

        keyGenerateComponent = new KeySymmetricalGenerateComponent();
        this.add(keyGenerateComponent);
        sizeController.addObserver(keyGenerateComponent);

        selectAlgorithmComponent = new SelectAlgorithmGenerateKeyComponent(onAlgorithmKeyChanged, KeyConfig.getInstance().getMapAlgorithmSymmetrical());
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
        buttonCreate.setPreferredSize(new Dimension(parentSize - 500, 50));
        panelSpace.setPreferredSize(new Dimension(parentSize - 200, 200));
    }
}
