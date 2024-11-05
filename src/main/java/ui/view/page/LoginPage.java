/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Created at: 11:09 AM - 23/10/2024
 * User: lam-nguyen
 */

package main.java.ui.view.page;

import main.java.ui.controller.INavigation;
import main.java.ui.model.IOService;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JPanel {
    private final INavigation navigation;
    private final IOService ioService;
    private JButton btnLogin;
    private JTextField inputUserName;
    private JTextField inputPassword;

    public LoginPage(INavigation navigation, IOService ioService) {
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
        btnLogin.addActionListener(e -> {
        String username = inputUserName.getText();
        String password = inputPassword.getText();
        ioService.sendMessage("login " + username + " " + password);
    });

        inputUserName = new JTextField("Nhập tên đăng nhập");
        inputUserName.setPreferredSize(new Dimension(300, 50));

        inputPassword = new JTextField("Nhập nhật khẩu");
        inputPassword.setPreferredSize(new Dimension(300, 50));

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(300, 500));

        JPanel innerPanel = new JPanel();
        innerPanel.setPreferredSize(new Dimension(300, 200));
        mainPanel.add(innerPanel);

        mainPanel.add(inputUserName);
        mainPanel.add(inputPassword);
        mainPanel.add(btnLogin);

        add(mainPanel);
    }
}
