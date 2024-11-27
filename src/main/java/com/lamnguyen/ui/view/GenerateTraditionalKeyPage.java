/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:40 AM - 11/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.helper.DialogProgressHelper;
import com.lamnguyen.helper.SettingHelper;
import com.lamnguyen.helper.ValidationHelper;
import com.lamnguyen.model.traditionalCipher.ITraditionalCipher;
import com.lamnguyen.model.traditionalCipher.TraditionalKey;
import com.lamnguyen.model.traditionalCipher.algorithm.AffineCipher;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.output.OutputComponent;
import com.lamnguyen.ui.component.selector.SelectAlgorithmGenerateTraditionalKeyComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class GenerateTraditionalKeyPage extends JPanel implements Observer {
    private ITraditionalCipher cipher;
    private OutputInputTextComponent outputKey;
    private SelectAlgorithmGenerateTraditionalKeyComponent selectAlgorithmComponent;
    private OutputComponent outputComponent;
    private SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private JButton buttonCreate;
    private Application application;
    private JComboBox<ITraditionalCipher.SecureLanguage> jcbLanguage;
    private final int V_GAP = 20;

    public GenerateTraditionalKeyPage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, V_GAP));

        Function<SelectAlgorithmGenerateTraditionalKeyComponent.AlgorithmKey, Void> onAlgorithmKeyChanged = algorithmKey -> {
            if (algorithmKey == null)
                outputComponent.setFileName("");
            else
                outputComponent.setFileName(algorithmKey.getName() + "_" + jcbLanguage.getSelectedItem() + (algorithmKey.getSize().isBlank() ? "" : "_" + algorithmKey.getSize()));
            cipher = null;
            return null;
        };

        outputKey = new OutputInputTextComponent("khóa khởi tạo") {{
            setEditable(false);
            clickToCopy(true);
        }}; // ?
        this.add(outputKey);

        selectAlgorithmComponent = new SelectAlgorithmGenerateTraditionalKeyComponent(onAlgorithmKeyChanged); //150
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        jcbLanguage = new JComboBox<>(ITraditionalCipher.SecureLanguage.values()) {{
            addActionListener(actionEvent -> {
                cipher = null;
                var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
                if (algorithmKey == null)
                    outputComponent.setFileName("");
                else
                    outputComponent.setFileName(algorithmKey.getName() + "_" + jcbLanguage.getSelectedItem() + (algorithmKey.getSize().isBlank() ? "" : "_" + algorithmKey.getSize()));
            });
        }}; // 50
        this.add(jcbLanguage);

        buttonCreate = new JButton("Tạo khóa!") {{
            addActionListener(actionEvent -> {
                var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
                if (algorithmKey == null) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập size key!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                var name = ITraditionalCipher.Algorithms.valueOf(algorithmKey.getName());
                var lang = (ITraditionalCipher.SecureLanguage) jcbLanguage.getSelectedItem();
                var size = algorithmKey.getSize();
                cipher = ITraditionalCipher.Factory.createEncrypt(name, lang);
                generateKey(size);
            });
        }}; //50
        this.add(buttonCreate);


        var algorithmKey = selectAlgorithmComponent.getAlgorithmKey();
        outputComponent = new OutputComponent() {{
            setPathFolder(SettingHelper.getInstance().getWorkSpace() + "/key");
            setTextButtonAction("Xuất file");
            setFileName(algorithmKey.getName() + "_" + jcbLanguage.getSelectedItem() + (algorithmKey.getSize().isBlank() ? "" : "_" + algorithmKey.getSize()));
            setExtensionFile(".keys");
            setActionButtonAction(actionEvent -> saveKey());
        }}; // 110
        this.add(outputComponent);
        sizeController.addObserver(outputComponent);
    }

    private void generateKey(String size) {
        DialogProgressHelper.runProcess(process -> {
            TraditionalKey<?> key;
            try {
                key = cipher.generateKey(size);
                cipher.loadTraditionalKey(key);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Tạo khóa thất bại.", "Error", JOptionPane.ERROR_MESSAGE);
                process.dispose();
                return;
            }

            var contentKey = key.contentKey();
            switch (contentKey) {
                case Integer intKey -> {
                    outputKey.setTextJTextArea(String.valueOf(intKey));
                }
                case HashMap mapKey -> {
                    var hash = (HashMap<Character, Character>) mapKey;
                    var arrKey = hash.entrySet().stream().map(entry -> entry.getKey() + " -> " + entry.getValue()).toArray(String[]::new);
                    outputKey.setTextJTextArea(String.join("\n", arrKey));
                }
                case AffineCipher.AffineKey affineKey -> {
                    outputKey.setTextJTextArea("A: " + affineKey.a() + "\nB: " + affineKey.b());
                }
                case String string -> {
                    outputKey.setTextJTextArea(string);
                }
                default -> {
                    var k = (int[][]) contentKey;
                    var stringKey = new StringBuilder();
                    for (var row : k)
                        stringKey.append("|\t").append(String.join("\t|\t", Arrays.stream(row).mapToObj(String::valueOf).toArray(String[]::new))).append("\t|\n");
                    outputKey.setTextJTextArea(stringKey.toString());
                }
            }

            process.dispose();
        });
    }

    private void saveKey() {
        DialogProgressHelper.runProcess(process -> {
            if (!ValidationHelper.validateKeyGenerate(cipher, process))
                return;


            try {
                var file = new File(outputComponent.getFolderDest());
                if (!file.exists() && !file.mkdirs()) throw new IOException();
                cipher.saveKey(outputComponent.getFullPath());
                if (outputComponent.getFullPath().startsWith(SettingHelper.getInstance().getWorkSpace()))
                    application.reloadWorkSpaceSync();
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Lưu file tất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            process.dispose();
        });

    }

    @Override
    public void update(Observable observable, Object o) {
        var parentSize = getParent().getWidth();
        buttonCreate.setPreferredSize(new Dimension(parentSize - 400, 50));
        jcbLanguage.setPreferredSize(new Dimension(parentSize - 200, 50));
        var size = getParent().getHeight() - V_GAP * 6 - 110 - 50 * 2 - 130;
        outputKey.setCustomSize(new Dimension(parentSize - 500, size));
    }
}
