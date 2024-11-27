/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.config.HashAlgorithmConfig;
import com.lamnguyen.helper.DialogProgressHelper;
import com.lamnguyen.helper.ValidationHelper;
import com.lamnguyen.model.hash.IHash;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.dropAndDrag.DropAndDragComponent;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.selector.SelectHashAlgorithmComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class HashPage extends JPanel implements Observer {
    private final Application application;
    private DropAndDragComponent dropAndDragComponent;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private SelectHashAlgorithmComponent selectHashAlgorithmComponent;
    private OutputInputTextComponent output;
    private JButton action;
    private final int V_GAP = 20;
    private OutputInputTextComponent inputTextComponent;
    private JPanel panelInput;
    private CardLayout cardLayoutPanelInput;
    private boolean fileMode = true;

    public HashPage(Application application) {
        this.application = application;
        this.init();
        this.event();
    }

    private void init() {
        this.setOpaque(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, V_GAP));

        cardLayoutPanelInput = new CardLayout();
        panelInput = new JPanel(cardLayoutPanelInput) {{
            setOpaque(false);
        }};
        this.add(panelInput);

        Function<String, Void> onFileChange = file -> {
            setHashString("");
            return null;
        };

        dropAndDragComponent = new DropAndDragComponent(onFileChange);
        panelInput.add(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)) {{
            setOpaque(false);
            add(dropAndDragComponent);
        }}, "file");

        inputTextComponent = new OutputInputTextComponent("Nhập văn bản");
        panelInput.add(inputTextComponent, "text");

        selectHashAlgorithmComponent = new SelectHashAlgorithmComponent(this);
        sizeController.addObserver(selectHashAlgorithmComponent);
        this.add(selectHashAlgorithmComponent);

        action = new JButton() {{
            setPreferredSize(new Dimension(500, 50));
            addActionListener(actionEvent -> {
                if (fileMode) hashFile();
                else hashText();
            });
        }};
        this.add(action);


        output = new OutputInputTextComponent("Mã hash") {{
            setEditable(false);
            clickToCopy(true);
        }};
        this.add(output);

        hashFileMode();
    }

    private void event() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control F"), "Ctrl_F");
        getActionMap().put("Ctrl_F", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashPage.this.hashFileMode();
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control T"), "Ctrl_T");
        getActionMap().put("Ctrl_T", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashPage.this.hashTextMode();
            }
        });
    }

    private void hashFile() {
        DialogProgressHelper.runProcess(process -> {
            var file = dropAndDragComponent.getFile();
            if (!ValidationHelper.validateFile(file, process))
                return;

            var alg = selectHashAlgorithmComponent.getAlgorithm();

            try {
                setHashString(IHash.Factory.getInstance(alg).hashFile(file.getAbsolutePath()));
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            process.dispose();
        });
    }

    private void hashText() {
        DialogProgressHelper.runProcess(process -> {
            var text = inputTextComponent.getText();
            if (!ValidationHelper.validateText(text, process))
                return;

            var alg = selectHashAlgorithmComponent.getAlgorithm();

            try {
                setHashString(IHash.Factory.getInstance(alg).hashText(text));
                JOptionPane.showMessageDialog(null, "Thành công!");
            } catch (Exception ignored) {
                ignored.printStackTrace(System.out);
                JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            process.dispose();
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        var sizeParent = this.getParent().getSize();
        output.setCustomSize(new Dimension(sizeParent.width - 200, 150));
        var height = sizeParent.height - V_GAP * 5 - 50 - 100 - output.getHeight();
        panelInput.setPreferredSize(new Dimension(sizeParent.width - 200, height));
        panelInput.setSize(panelInput.getPreferredSize());
        dropAndDragComponent.setCustomSize(new Dimension(sizeParent.width - 400, height));
        inputTextComponent.setCustomSize(panelInput.getSize());
    }

    public void hashFileMode() {
        if (fileMode) return;
        fileMode = true;
        cardLayoutPanelInput.show(panelInput, "file");
        action.setText("Hash file");
        setHashString("");
        selectHashAlgorithmComponent.setListAlg(HashAlgorithmConfig.getInstance().getHashAlgForFile());
    }

    public void hashTextMode() {
        if (!fileMode) return;
        fileMode = false;
        cardLayoutPanelInput.show(panelInput, "text");
        action.setText("Hash văn bản");
        setHashString("");
        selectHashAlgorithmComponent.setListAlg(HashAlgorithmConfig.getInstance().getHashAlgForText());
    }

    public void setHashString(String s) {
        output.setTextJTextArea(s);
    }
}
