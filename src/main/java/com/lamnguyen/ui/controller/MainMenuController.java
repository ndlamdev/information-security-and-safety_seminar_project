/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:06 PM - 10/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.controller;

import com.lamnguyen.ui.component.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener {
    private MainMenu mainMenu;

    public MainMenuController(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JMenuItem jMenuItem = (JMenuItem) actionEvent.getSource();
        if (jMenuItem.equals(mainMenu.getExitMenuItem())) {
            mainMenu.exit();
            return;
        }

        if (jMenuItem.equals(mainMenu.getEncryptFileSymmetricalMenuItem())) {
            mainMenu.changePageEncryptSymmetricalFile();
            return;
        }

        if (jMenuItem.equals(mainMenu.getDecryptFileSymmetricalMenuItem())) {
            mainMenu.changePageDecryptSymmetricalFile();
            return;
        }

        if (jMenuItem.equals(mainMenu.getEncryptTextSymmetricalMenuItem())) {
            mainMenu.changePageEncryptSymmetricalText();
            return;
        }

        if (jMenuItem.equals(mainMenu.getDecryptTextSymmetricalMenuItem())) {
            mainMenu.changePageDecryptSymmetricalText();
            return;
        }
        
        if (jMenuItem.equals(mainMenu.getEncryptTextTraditionalMenuItem())) {
            mainMenu.changePageEncryptTraditionalText();
            return;
        }

        if (jMenuItem.equals(mainMenu.getDecryptTextTraditionalMenuItem())) {
            mainMenu.changePageDecryptTraditionalText();
            return;
        }

        if (jMenuItem.equals(mainMenu.getEncryptTextAsymmetricalMenuItem())) {
            mainMenu.changePageEncryptAsymmetricalText();
            return;
        }

        if (jMenuItem.equals(mainMenu.getDecryptTextAsymmetricalMenuItem())) {
            mainMenu.changePageDecryptAsymmetricalText();
            return;
        }

        if (jMenuItem.equals(mainMenu.getSignFileMenuItem())) {
            mainMenu.changePageSignFile();
            return;
        }

        if (jMenuItem.equals(mainMenu.getVerifySignatureFileMenuItem())) {
            mainMenu.changePageVerifySignatureFile();
            return;
        }

        if (jMenuItem.equals(mainMenu.getHashMenuItem())) {
            mainMenu.changePageHashFile();
            return;
        }

        if (jMenuItem.equals(mainMenu.getSymmetricalKeyMenuItem())) {
            mainMenu.changePageGenerateKeySymmetrical();
            return;
        }

        if (jMenuItem.equals(mainMenu.getAsymmetricalKeyMenuItem())) {
            mainMenu.changePageGenerateKeyAsymmetrical();
            return;
        }

        if (jMenuItem.equals(mainMenu.getTraditionalKey())) {
            mainMenu.changePageGenerateTraditionalKey();
            return;
        }

        if (jMenuItem.equals(mainMenu.getOpenWorkSpace())) {
            mainMenu.selectWorkSpace();
            return;
        }
    }
}
