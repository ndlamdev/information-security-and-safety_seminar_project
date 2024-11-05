package main.java.ui.view;

import main.java.ui.controller.INavigation;
import main.java.ui.controller.impl.Navigation;
import main.java.ui.model.IOService;
import main.java.ui.view.page.AlgorithmPage;
import main.java.ui.view.page.ChatPage;
import main.java.ui.view.page.LoginPage;
import main.java.ui.view.page.WelcomePage;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Application extends JFrame {
    private JPanel container;
    private CardLayout layout;
    private INavigation navigation;
    private final int enterCode = 10;

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private IOService ioService;

    public Application() {
        try {
            socket = new Socket("localhost", 1305);
            output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            ioService = new IOService(input, output);
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        setTitle("Kimi Chat TCP");
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        layout = new CardLayout();

        container = new JPanel(layout);
        navigation = new Navigation(layout, container);
        container.add(AlgorithmPage.class.getSimpleName(), new AlgorithmPage(navigation, ioService));
        container.add(WelcomePage.class.getSimpleName(), new WelcomePage(navigation, ioService));
        container.add(ChatPage.class.getSimpleName(), new ChatPage(navigation, ioService));
        container.add(LoginPage.class.getSimpleName(), new LoginPage(navigation, ioService));

        setContentPane(container);
        setBounds(new Rectangle(0, 0, kit.getScreenSize().width, kit.getScreenSize().height - 100));
        setResizable(false);
        setVisible(true);
    }
}