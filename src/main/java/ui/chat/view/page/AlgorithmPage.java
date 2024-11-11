/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Created at: 10:29 AM - 25/10/2024
 * User: lam-nguyen
 */

package main.java.ui.chat.view.page;

import main.java.ui.chat.controller.INavigation;
import main.java.ui.chat.model.IOService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AlgorithmPage extends JPanel {
    private final INavigation navigation;
    private final IOService ioService;
    private final List<String> algorithms = Arrays.asList("AES", "DES");

    public AlgorithmPage(INavigation navigation, IOService ioService) {
        this.navigation = navigation;
        this.ioService = ioService;
        init();
    }

    private void init() {
        JLabel welcomeLabel = new JLabel("Chào mừng bạn đến với Kimi chat!");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        welcomeLabel.setPreferredSize(new Dimension(screenSize.width, 200));
        welcomeLabel.setFont(new Font("", Font.BOLD, 50));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(150, 500));

        JPanel innerPanel = new JPanel();
        innerPanel.setPreferredSize(new Dimension(150, 200));
        buttonPanel.add(innerPanel);

        for (String algorithm : algorithms) {
            JButton algorithmButton = createButton(new JButton(algorithm));
            algorithmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    ioService.output().println("method_encrypt " + button.getText());
                    try {
                        if ("ok".equals(ioService.input().readLine())) {
                            navigation.push(INavigation.NamePage.WelcomePage);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            buttonPanel.add(algorithmButton);
        }

        add(buttonPanel);
    }

    private JButton createButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 50));
        return button;
    }
}
