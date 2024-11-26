/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.helper.DialogProgressHelper;
import com.lamnguyen.helper.ValidationHelper;
import com.lamnguyen.model.hash.impl.HashFileImpl;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.dropAndDrag.DropAndDragComponent;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.selector.SelectHashAlgorithmComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class HashFilePage extends JPanel implements Observer {
    private final Application application;
    private DropAndDragComponent dropAndDragComponent;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private SelectHashAlgorithmComponent selectHashAlgorithmComponent;
    private OutputInputTextComponent output;
    private JButton action;
    private final int V_GAP = 20;


    //1300
    public HashFilePage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, V_GAP));

        dropAndDragComponent = new DropAndDragComponent();
        sizeController.addObserver(dropAndDragComponent);
        this.add(dropAndDragComponent);

        selectHashAlgorithmComponent = new SelectHashAlgorithmComponent(this);
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
        }};
        this.add(output);
    }

    private void hashFile() {
        DialogProgressHelper.runProcess(process -> {
            var file = dropAndDragComponent.getFile();
            if (!ValidationHelper.validateFile(file, process))
                return;

            var alg = selectHashAlgorithmComponent.getAlgorithm();

            try {
                setHashString(HashFileImpl.getInstance(alg).hash(file.getAbsolutePath()));
            } catch (Exception ignored) {
            }
            process.dispose();
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        var sizeParent = this.getParent().getSize();
        output.setCustomSize(new Dimension(sizeParent.width - 200, 150));
        dropAndDragComponent.setCustomSize(new Dimension(sizeParent.width - 400, sizeParent.height - V_GAP * 5 - 50 - 100 - output.getHeight()));
    }

    public void setHashString(String s) {
        output.setTextJTextArea(s);
    }
}
