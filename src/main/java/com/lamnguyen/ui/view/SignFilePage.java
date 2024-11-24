/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.view;

import com.lamnguyen.config.SignatureAlgorithmConfig;
import com.lamnguyen.helper.DialogProgressHelper;
import com.lamnguyen.helper.ValidationHelper;
import com.lamnguyen.model.asymmetrical.AsymmetricalKey;
import com.lamnguyen.model.asymmetrical.IAsymmetrical;
import com.lamnguyen.model.signature.ISignFile;
import com.lamnguyen.model.signature.impl.SignFileImpl;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.component.dropAndDrag.DropAndDragComponent;
import com.lamnguyen.ui.component.input.OutputInputTextComponent;
import com.lamnguyen.ui.component.key.InputKeyComponent;
import com.lamnguyen.ui.component.selector.SelectSignatureAlgorithmComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.Observable;
import java.util.Observer;

public class SignFilePage extends JPanel implements Observer {
    private final Application application;
    private DropAndDragComponent dropAndDragComponent;
    private InputKeyComponent inputKeyComponent;
    private SelectSignatureAlgorithmComponent selectAlgorithmComponent;
    private final SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private AsymmetricalKey key;
    private OutputInputTextComponent resultComponent;
    private final int V_GAP = 20;


    public SignFilePage(Application application) {
        this.application = application;
        this.init();
    }

    private void init() {
        this.setOpaque(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, V_GAP));

        dropAndDragComponent = new DropAndDragComponent();
        this.add(dropAndDragComponent);

        inputKeyComponent = new InputKeyComponent(this::loadFileKey);//100
        this.add(inputKeyComponent);
        sizeController.addObserver(inputKeyComponent);

        selectAlgorithmComponent = new SelectSignatureAlgorithmComponent(SignatureAlgorithmConfig.getInstance().getAlgorithmSignature()); //150
        this.add(selectAlgorithmComponent);
        sizeController.addObserver(selectAlgorithmComponent);

        this.add(new JButton("Ký") {{
            setPreferredSize(new Dimension(500, 50));
            addActionListener(action -> signFile());
        }}); // 50

        resultComponent = new OutputInputTextComponent("Chữ kí file!") {{
            setEditable(false);
            clickToCopy(true);
        }};
        this.add(resultComponent);
    }


    private void signFile() {
        DialogProgressHelper.runProcess(process -> {
            var file = dropAndDragComponent.getPathFile();
            var alg = selectAlgorithmComponent.getAlgorithm();
            if (!ValidationHelper.validateFile(file, process))
                return;

            try {
                ISignFile signer = SignFileImpl.getInstance(alg, key.privateKey());
                resultComponent.setTextJTextArea(signer.sign(file));
            } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {
                process.dispose();
                JOptionPane.showMessageDialog(null, "Khóa không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException | SignatureException e) {
                JOptionPane.showMessageDialog(null, "Thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            process.dispose();
        });
    }

    public Void loadFileKey(File file) {
        if (file == null) return null;
        try {
            key = IAsymmetrical.KeyFactory.readKey(file.getAbsolutePath());
            inputKeyComponent.setPathFileKey(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Load key thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File key không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        var sizeParent = this.getParent().getSize();
        resultComponent.setCustomSize(new Dimension(sizeParent.width - 200, 70));
        dropAndDragComponent.setCustomSize(new Dimension(sizeParent.width - 400, sizeParent.height - V_GAP * 6 - 50 - 110 - 150 - resultComponent.getHeight()));
    }
}
