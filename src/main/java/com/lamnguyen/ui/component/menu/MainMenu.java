/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:25 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.menu;

import lombok.Getter;
import com.lamnguyen.ui.Application;
import com.lamnguyen.ui.controller.MainMenuController;
import com.lamnguyen.ui.controller.navigation.IJNavigation;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JMenuBar {
    private IJNavigation navigation;
    private Application application;
    private MainMenuController mainMenuController;
    @Getter
    private JMenuItem openWorkSpace,
            encryptFileSymmetricalMenuItem,
            decryptFileSymmetricalMenuItem,
            encryptFileAsymmetricalMenuItem,
            decryptFileAsymmetricalMenuItem,
            encryptTextSymmetricalMenuItem,
            decryptTextSymmetricalMenuItem,
            encryptTextAsymmetricalMenuItem,
            decryptTextAsymmetricalMenuItem,
            encryptTextTraditionalMenuItem,
            decryptTextTraditionalMenuItem,
            exitMenuItem,
            symmetricalKeyMenuItem,
            asymmetricalKeyMenuItem,
            traditionalKey,
            hashMenuItem,
            signFileMenuItem,
            verifySignatureFileMenuItem;

    @Getter
    private JMenu symmetricalFileMenu,
            asymmetricalFileMenu,
            symmetricalTextMenu,
            asymmetricalTextMenu,
            traditionalTextMenu,
            homeMenu,
            generateKeyMenu,
            fileMenu,
            textMenu,
            signatureMenu,
            aboutMenu;

    public MainMenu(IJNavigation navigation, Application application) {
        this.navigation = navigation;
        this.application = application;
        this.initMenu();
        this.initMenuItemHome();
        this.initMenuItemFile();
        this.initMenuItemText();
        this.initMenuItemSignatureFile();
        this.initMenuItemGenerateKey();
    }

    private void initMenu() {
        this.mainMenuController = new MainMenuController(this);

        homeMenu = new JMenu("Home");
        this.add(homeMenu);

        generateKeyMenu = new JMenu("Tạo key");
        this.add(generateKeyMenu);

        fileMenu = new JMenu("File");
        this.add(fileMenu);

        textMenu = new JMenu("Văn bản");
        this.add(textMenu);

        hashMenuItem = new JMenu("Hash") {{
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    navigation.push(IJNavigation.NamePage.HashFilePage);
                }
            });
        }};
        this.add(hashMenuItem);

        signatureMenu = new JMenu("Chử ký điện tử");
        this.add(signatureMenu);

        aboutMenu = new JMenu("About");
        this.add(aboutMenu);
    }

    private void initMenuItemFile() {
        symmetricalFileMenu = new JMenu("Đối xứng");
        fileMenu.add(symmetricalFileMenu);

        encryptFileSymmetricalMenuItem = new JMenuItem("Mã hóa") {{
            addActionListener(mainMenuController);
        }};
        symmetricalFileMenu.add(encryptFileSymmetricalMenuItem);

        decryptFileSymmetricalMenuItem = new JMenuItem("Giải mã") {{
            addActionListener(mainMenuController);
        }};
        symmetricalFileMenu.add(decryptFileSymmetricalMenuItem);

        asymmetricalFileMenu = new JMenu("Bất đối xứng");
        fileMenu.add(asymmetricalFileMenu);

        encryptFileAsymmetricalMenuItem = new JMenuItem("Mã hóa") {{
            addActionListener(mainMenuController);
        }};
        asymmetricalFileMenu.add(encryptFileAsymmetricalMenuItem);

        decryptFileAsymmetricalMenuItem = new JMenuItem("Giải mã") {{
            addActionListener(mainMenuController);
        }};
        asymmetricalFileMenu.add(decryptFileAsymmetricalMenuItem);
    }

    private void initMenuItemText() {
        symmetricalTextMenu = new JMenu("Đối xứng");
        textMenu.add(symmetricalTextMenu);

        encryptTextSymmetricalMenuItem = new JMenuItem("Mã hóa") {{
            addActionListener(mainMenuController);
        }};
        symmetricalTextMenu.add(encryptTextSymmetricalMenuItem);

        decryptTextSymmetricalMenuItem = new JMenuItem("Giải mã") {{
            addActionListener(mainMenuController);
        }};
        symmetricalTextMenu.add(decryptTextSymmetricalMenuItem);

        asymmetricalTextMenu = new JMenu("Bất đối xứng");
        textMenu.add(asymmetricalTextMenu);

        encryptTextAsymmetricalMenuItem = new JMenuItem("Mã hóa") {{
            addActionListener(mainMenuController);
        }};
        asymmetricalTextMenu.add(encryptTextAsymmetricalMenuItem);

        decryptTextAsymmetricalMenuItem = new JMenuItem("Giải mã") {{
            addActionListener(mainMenuController);
        }};
        asymmetricalTextMenu.add(decryptTextAsymmetricalMenuItem);

        traditionalTextMenu = new JMenu("Tự nhiên");
        textMenu.add(traditionalTextMenu);

        encryptTextTraditionalMenuItem = new JMenuItem("Mã hóa") {{
            addActionListener(mainMenuController);
        }};
        traditionalTextMenu.add(encryptTextTraditionalMenuItem);

        decryptTextTraditionalMenuItem = new JMenuItem("Giải mã") {{
            addActionListener(mainMenuController);
        }};
        traditionalTextMenu.add(decryptTextTraditionalMenuItem);
    }

    private void initMenuItemHome() {
        openWorkSpace = new JMenuItem("Mở không gian làm việc") {{
            addActionListener(mainMenuController);
        }};
        homeMenu.add(openWorkSpace);

        homeMenu.addSeparator();
        exitMenuItem = new JMenuItem("Thoát") {{
            addActionListener(actionEvent -> {
                System.exit(0);
            });
        }};
        homeMenu.add(exitMenuItem);
    }

    private void initMenuItemSignatureFile() {
        signFileMenuItem = new JMenuItem("Ký") {{
            addActionListener(mainMenuController);
        }};
        signatureMenu.add(signFileMenuItem);

        verifySignatureFileMenuItem = new JMenuItem("Xác thực") {{
            addActionListener(mainMenuController);
        }};
        signatureMenu.add(verifySignatureFileMenuItem);
    }

    private void initMenuItemGenerateKey() {
        symmetricalKeyMenuItem = new JMenuItem("Khóa đối xứng") {{
            addActionListener(mainMenuController);
        }};
        generateKeyMenu.add(symmetricalKeyMenuItem);

        asymmetricalKeyMenuItem = new JMenuItem("Khóa bất đối xứng") {{
            addActionListener(mainMenuController);
        }};
        generateKeyMenu.add(asymmetricalKeyMenuItem);

        traditionalKey = new JMenuItem("Khóa mã hóa tự nhiên") {{
            addActionListener(mainMenuController);
        }};
        generateKeyMenu.add(traditionalKey);
    }

    public void changePageEncryptSymmetricalFile() {
        application.encryptFileSymmetrical();
        navigation.push(IJNavigation.NamePage.CipherFileSymmetricalPage);
    }

    public void changePageDecryptSymmetricalFile() {
        application.decryptFileSymmetrical();
        navigation.push(IJNavigation.NamePage.CipherFileSymmetricalPage);
    }

    public void changePageEncryptSymmetricalText() {
        application.encryptTextSymmetrical();
        navigation.push(IJNavigation.NamePage.CipherTextSymmetricalPage);
    }

    public void changePageDecryptSymmetricalText() {
        application.decryptTextSymmetrical();
        navigation.push(IJNavigation.NamePage.CipherTextSymmetricalPage);
    }

    public void exit() {
        System.exit(0);
    }

    public void changePageGenerateKeySymmetrical() {
        navigation.push(IJNavigation.NamePage.GenerateKeySymmetricalPage);
    }

    public void selectWorkSpace() {
        application.selectWorkSpace();
    }

    public void changePageGenerateKeyAsymmetrical() {
        navigation.push(IJNavigation.NamePage.GenerateKeyAsymmetricalPage);
    }

    public void changePageEncryptAsymmetricalText() {
        application.encryptTextAsymmetrical();
        navigation.push(IJNavigation.NamePage.CipherTextAsymmetricalPage);
    }

    public void changePageDecryptAsymmetricalText() {
        application.decryptTextAsymmetrical();
        navigation.push(IJNavigation.NamePage.CipherTextAsymmetricalPage);
    }

    public void changePageSignFile() {
        navigation.push(IJNavigation.NamePage.SignFilePage);
    }

    public void changePageVerifySignatureFile() {
        navigation.push(IJNavigation.NamePage.VerifySignatureFilePage);
    }

    public void changePageHashFile() {
        navigation.push(IJNavigation.NamePage.HashFilePage);
    }

    public void changePageGenerateTraditionalKey() {
        navigation.push(IJNavigation.NamePage.GenerateTraditionalKeyPage);
    }

    public void changePageEncryptTraditionalText() {
        application.encryptTextTraditional();
        navigation.push(IJNavigation.NamePage.CipherTextTraditionalPage);
    }

    public void changePageDecryptTraditionalText() {
        application.decryptTextTraditional();
        navigation.push(IJNavigation.NamePage.CipherTextTraditionalPage);
    }
}
