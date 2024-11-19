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
import com.lamnguyen.security.traditionalCipher.ITraditionalCipher;
import com.lamnguyen.security.traditionalCipher.TraditionalKey;
import com.lamnguyen.security.traditionalCipher.encrypt.ATraditionalEncrypt;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.output.OutputComponent;
import com.lamnguyen.ui.component.selector.SelectAlgorithmGenerateKeyComponent;
import com.lamnguyen.ui.component.selector.SelectAlgorithmGenerateTraditionalKeyComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class GenerateTraditionalKeyPage extends JPanel implements Observer {
    private OutputInputTextComponent outputKey;
    private SelectAlgorithmGenerateTraditionalKeyComponent selectAlgorithmComponent;
    private OutputComponent outputComponent;
    private SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private JButton buttonCreate;
    private JPanel panelSpace;
    private Application application;
    private ATraditionalEncrypt encrypt;

    public GenerateTraditionalKeyPage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));

        Function<SelectAlgorithmGenerateTraditionalKeyComponent.AlgorithmKey, Void> onAlgorithmKeyChanged = algorithmKey -> {
            if (algorithmKey == null)
                outputComponent.setFileName("");
            else
                outputComponent.setFileName(algorithmKey.getName() + (algorithmKey.getSize().isBlank() ? "" : "_" + algorithmKey.getSize()));
            encrypt = null;
            return null;
        };

        outputKey = new OutputInputTextComponent("khóa khởi tạo", new Dimension(1300, 300)) {{
            setEditable(false);
            clickToCopy(true);
        }};
        this.add(outputKey);

        selectAlgorithmComponent = new SelectAlgorithmGenerateTraditionalKeyComponent(onAlgorithmKeyChanged);
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        buttonCreate = new JButton("Tạo khóa!") {{
            addActionListener(actionEvent -> {
                var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
                if (algorithmKey == null) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập size key!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                var name = ITraditionalCipher.KeyFactory.Algorithms.valueOf(algorithmKey.getName());
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
            setFileName(algorithmKey.getName() + (algorithmKey.getSize().isBlank() ? "" : "_" + algorithmKey.getSize()));
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
        panelSpace.setPreferredSize(new Dimension(parentSize - 200, 50));
    }
}
