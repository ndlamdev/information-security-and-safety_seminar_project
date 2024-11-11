/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:20 PM - 10/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.controller;

import lombok.SneakyThrows;
import main.java.ui.fileEncrypt.component.key.InputKeyComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyComponentController implements ActionListener {
    private InputKeyComponent keyComponent;

    public KeyComponentController(InputKeyComponent keyComponent) {
        this.keyComponent = keyComponent;
    }

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton button = (JButton) actionEvent.getSource();
        if (button.equals(keyComponent.getJbtSelectFileKey())) {
            keyComponent.loadFileKey();
        }
    }
}
