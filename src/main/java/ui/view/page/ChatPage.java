/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Created at: 9:54 AM - 25/10/2024
 * User: lam-nguyen
 */

package main.java.ui.view.page;

import main.java.ui.controller.INavigation;
import main.java.ui.model.IOService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ChatPage extends JPanel {
    private static final int ENTER_CODE = KeyEvent.VK_ENTER;
    private final INavigation navigation;
    private final IOService ioService;
    private final List<String> messages = new ArrayList<>();

    public ChatPage(INavigation navigation, IOService ioService) {
        this.navigation = navigation;
        this.ioService = ioService;
        init();
    }

    private void init() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLayout(new BorderLayout());

        JPanel panelMessage = new JPanel();
        Dimension preferredSize = panelMessage.getPreferredSize();
        preferredSize.width = kit.getScreenSize().width - 20;
        preferredSize.height = 0;
        panelMessage.setPreferredSize(preferredSize);

        JScrollPane scrollPane = new JScrollPane(panelMessage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        JTextField inputField = new JTextField();
        Dimension inputPreferredSize = inputField.getPreferredSize();
        inputPreferredSize.width = kit.getScreenSize().width - 100;
        inputPreferredSize.height = 50;
        inputField.setPreferredSize(inputPreferredSize);

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent key) {
                if (ENTER_CODE != key.getKeyCode()) return;
                String text = inputField.getText();
                inputField.setText("");
                if (text.isBlank()) return;

                Dimension panelPreferredSize = panelMessage.getPreferredSize();
                panelPreferredSize.height += 50;
                panelMessage.setPreferredSize(panelPreferredSize);
                panelMessage.add(printMessage(text, MessageRole.SENDER));
                panelMessage.revalidate();
                panelMessage.repaint();
            }
        });

        JButton sendButton = new JButton("Gửi");
        Dimension buttonPreferredSize = sendButton.getPreferredSize();
        buttonPreferredSize.height = 50;
        sendButton.setPreferredSize(buttonPreferredSize);

        inputPanel.add(inputField);
        inputPanel.add(sendButton);
        this.add(inputPanel, BorderLayout.SOUTH);
    }

    public enum MessageRole {
            SENDER, RECEIVER
    }

    public JPanel printMessage(String text, MessageRole role) {
        JPanel messagePanel = new JPanel();
        Toolkit kit = Toolkit.getDefaultToolkit();
        messagePanel.setLayout(new BorderLayout());
        Dimension preferredSize = messagePanel.getPreferredSize();
        preferredSize.width = kit.getScreenSize().width - 20;
        preferredSize.height = 50;
        messagePanel.setPreferredSize(preferredSize);

        JLabel messageLabel = new JLabel(text);
        messagePanel.add(messageLabel, role == MessageRole.SENDER ? BorderLayout.EAST : BorderLayout.WEST);
        return messagePanel;
    }
}
