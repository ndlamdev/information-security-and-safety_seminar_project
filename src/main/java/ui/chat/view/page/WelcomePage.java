/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Created at: 11:09 AM - 23/10/2024
 * User: lam-nguyen
 */

package main.java.ui.chat.view.page;

import main.java.ui.chat.controller.INavigation;
import main.java.ui.chat.model.IOService;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JPanel {
    private final INavigation navigation;
    private final IOService ioService;
    private JButton btnLogin;
    private JButton btnSignUp;

    public WelcomePage(INavigation navigation, IOService ioService) {
        this.navigation = navigation;
        this.ioService = ioService;
        init();
    }

    private void init() {
        JLabel welcomeLabel = new JLabel("Chào mừng bạn đến với Kimi chat!");
        welcomeLabel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 200));
        welcomeLabel.setFont(new Font("", Font.BOLD, 50));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setPreferredSize(new Dimension(150, 50));
        btnLogin.addActionListener(e -> navigation.push(INavigation.NamePage.LoginPage));

        btnSignUp = new JButton("Đăng ký");
        btnSignUp.setPreferredSize(new Dimension(150, 50));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(150, 500));

        JPanel innerPanel = new JPanel();
        innerPanel.setPreferredSize(new Dimension(150, 200));
        buttonPanel.add(innerPanel);

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnSignUp);

        add(buttonPanel);
    }
}
