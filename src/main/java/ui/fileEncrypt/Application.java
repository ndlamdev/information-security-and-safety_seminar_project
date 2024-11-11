/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:11 AM - 08/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt;

import main.java.ui.fileEncrypt.component.menu.MainMenu;
import main.java.ui.fileEncrypt.controller.navigation.IJNavigation;
import main.java.ui.fileEncrypt.controller.navigation.impl.JNavigation;
import main.java.ui.fileEncrypt.view.GenerateKeySymmetricalPage;
import main.java.ui.fileEncrypt.view.MainPage;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {
    private final Toolkit toolkit;
    private JPanel container;
    private IJNavigation navigation;
    private MainPage mainPage;

    Application() {
        toolkit = Toolkit.getDefaultToolkit();
        this.init();
    }

    private void init() {
        this.setTitle("Phần mềm mã hóa/giải mã file Lam Nguyên");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 50, toolkit.getScreenSize().width - 200, toolkit.getScreenSize().height - 100);
        this.setResizable(false);

        CardLayout layout = new CardLayout();
        container = new JPanel(layout);
        navigation = new JNavigation(layout, container);

        this.setJMenuBar(new MainMenu(navigation, this));

        mainPage = new MainPage();
        container.add(IJNavigation.NamePage.MainPage.name(), mainPage);
        container.add(IJNavigation.NamePage.GenerateKeySymmetricalPage.name(), new GenerateKeySymmetricalPage());

        this.setContentPane(container);
        this.setVisible(true);
    }

    public void encryptFile() {
        mainPage.encryptMode();
    }

    public void decryptFile() {
        mainPage.decryptMode();
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        new Application();
    }
}
