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
    private JMenuItem openWorkSpace, encryptSymmetricalMenuItem, decryptSymmetricalMenuItem, exitMenuItem, keySymmetrical, keyAsymmetrical;
    @Getter
    private JMenu symmetricalMenu, home, file, text;

    public MainMenu(IJNavigation navigation, Application application) {
        this.navigation = navigation;
        this.application = application;
        this.initMenu();
        this.initMenuHome();
        this.initMenuFile();
    }

    private void initMenu() {
        this.mainMenuController = new MainMenuController(this);

        home = new JMenu("Home");
        this.add(home);

        file = new JMenu("File");
        this.add(file);

        text = new JMenu("Văn bản");
        this.add(text);

        JMenu about = new JMenu("About");
        this.add(about);
    }

    private void initMenuFile() {
        symmetricalMenu = new JMenu("Bất đối xứng!");
        file.add(symmetricalMenu);

        encryptSymmetricalMenuItem = new JMenuItem("Mã hóa!") {{
            addActionListener(mainMenuController);
        }};
        symmetricalMenu.add(encryptSymmetricalMenuItem);

        decryptSymmetricalMenuItem = new JMenuItem("Giải mã!") {{
            addActionListener(mainMenuController);
        }};
        symmetricalMenu.add(decryptSymmetricalMenuItem);
    }

    private void initMenuHome() {
        openWorkSpace = new JMenuItem("Mở không gian làm việc!");
        home.add(openWorkSpace);

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
    }

    public void changePageEncryptSymmetricalFile() {
        application.encryptFile();
        navigation.push(IJNavigation.NamePage.SymmetricalPage);
    }

    public void changePageDecryptSymmetricalFile() {
        application.decryptFile();
        navigation.push(IJNavigation.NamePage.SymmetricalPage);
    }

    public void exit() {
        System.exit(0);
    }

    public void changePageGenerateKeySymmetrical() {
        navigation.push(IJNavigation.NamePage.GenerateKeySymmetricalPage);
    }
}
