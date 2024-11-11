/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:25 AM - 08/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.component.menu;

import lombok.Getter;
import main.java.ui.fileEncrypt.Application;
import main.java.ui.fileEncrypt.controller.MainMenuController;
import main.java.ui.fileEncrypt.controller.navigation.IJNavigation;

import javax.swing.*;

public class MainMenu extends JMenuBar {
    private IJNavigation navigation;
    private Application application;
    private MainMenuController mainMenuController;
    @Getter
    private JMenuItem encryptMenuItem, decryptMenuItem, exitMenuItem, keySymmetrical, keyAsymmetrical;

    public MainMenu(IJNavigation navigation, Application application) {
        this.navigation = navigation;
        this.application = application;
        this.init();
    }

    private void init() {
        this.mainMenuController = new MainMenuController(this);

        JMenu home = new JMenu("Home");
        this.add(home);

        encryptMenuItem = new JMenuItem("Mã hóa file") {{
            addActionListener(mainMenuController);
        }};
        home.add(encryptMenuItem);

        decryptMenuItem = new JMenuItem("Giải mã file") {{
            addActionListener(mainMenuController);
        }};
        home.add(decryptMenuItem);

        home.addSeparator();
        var generateKeyMenu = new JMenu("Tạo key");
        home.add(generateKeyMenu);

        keySymmetrical = new JMenuItem("Khóa đối xứng!") {{
            addActionListener(mainMenuController);
        }};
        generateKeyMenu.add(keySymmetrical);

        keyAsymmetrical = new JMenuItem("Khóa bất đối xứng!") {{
            addActionListener(mainMenuController);
        }};
        generateKeyMenu.add(keyAsymmetrical);

        home.addSeparator();
        exitMenuItem = new JMenuItem("Thoát!") {{
            addActionListener(actionEvent -> {
                System.exit(0);
            });
        }};
        home.add(exitMenuItem);

        JMenu about = new JMenu("About");
        this.add(about);
    }

    public void changePageEncryptFile() {
        application.encryptFile();
        navigation.push(IJNavigation.NamePage.MainPage);
    }

    public void changePageDecryptFile() {
        application.decryptFile();
        navigation.push(IJNavigation.NamePage.MainPage);
    }

    public void exit() {
        System.exit(0);
    }

    public void changePageGenerateKeySymmetrical() {
        navigation.push(IJNavigation.NamePage.GenerateKeySymmetricalPage);
    }
}
