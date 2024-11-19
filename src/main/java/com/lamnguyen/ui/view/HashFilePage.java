/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.security.hash.IHashFile;
import com.lamnguyen.security.hash.impl.HashFileImpl;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.dropAndDrag.DropAndDragComponent;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.selector.SelectHashAlgorithmComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class HashFilePage extends JPanel {
    private final Application application;
    private DropAndDragComponent dropAndDragComponent;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private SelectHashAlgorithmComponent selectHashAlgorithmComponent;
    private OutputInputTextComponent output;
    private JButton action;


    //1300
    public HashFilePage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));

        dropAndDragComponent = new DropAndDragComponent();
        sizeController.addObserver(dropAndDragComponent);
        this.add(dropAndDragComponent);

        selectHashAlgorithmComponent = new SelectHashAlgorithmComponent();
        sizeController.addObserver(selectHashAlgorithmComponent);
        this.add(selectHashAlgorithmComponent);

        action = new JButton("Hash") {{
            setPreferredSize(new Dimension(500, 50));
            addActionListener(actionEvent -> hashFile());
        }};
        this.add(action);


        output = new OutputInputTextComponent("Mã hash") {{
            setEditable(false);
            clickToCopy(true);
            setSize(new Dimension(1100, 220));
        }};
        this.add(output);
    }

    private void hashFile() {
        var file = dropAndDragComponent.getFile();
        if (file == null) {
            JOptionPane.showMessageDialog(null, "Chưa chọn file mã hóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        var alg = selectHashAlgorithmComponent.getAlgorithm();

        try {
            output.setTextJTextArea(HashFileImpl.getInstance(alg).hash(file.getAbsolutePath()));
        } catch (Exception ignored) {
        }
    }
}
