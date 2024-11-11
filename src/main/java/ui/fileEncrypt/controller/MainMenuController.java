/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:06 PM - 10/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.controller;

import main.java.ui.fileEncrypt.Application;
import main.java.ui.fileEncrypt.component.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener {
    private MainMenu mainMenu;

    public MainMenuController(MainMenu mainMenu
    ) {
        this.mainMenu = mainMenu;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JMenuItem jMenuItem = (JMenuItem) actionEvent.getSource();
        if (jMenuItem.equals(mainMenu.getExitMenuItem())) {
            mainMenu.exit();
            return;
        }

        if (jMenuItem.equals(mainMenu.getEncryptMenuItem())) {
            mainMenu.changePageEncryptFile();
            return;
        }

        if (jMenuItem.equals(mainMenu.getDecryptMenuItem())) {
            mainMenu.changePageDecryptFile();
            return;
        }

        if (jMenuItem.equals(mainMenu.getKeySymmetrical())) {
            mainMenu.changePageGenerateKeySymmetrical();
            return;
        }
    }
}
