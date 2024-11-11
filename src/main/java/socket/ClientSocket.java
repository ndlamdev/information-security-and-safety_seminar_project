/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:30 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.socket;

import lombok.Getter;
import main.java.model.User;
import main.java.service.IAuthenticationService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;
import java.util.UUID;

public class ClientSocket extends Thread {
    @Getter
    private final String clientSocketId = UUID.randomUUID().toString();
    private final BufferedReader input;
    private final PrintWriter output;
    private Channel channel;
    private boolean login = false;
    private IAuthenticationService authenticationService;
    private String methodEncrypt;
    private final Server server;

    public ClientSocket(Server server, Socket socket) throws Exception {
        this.server = server;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    @Override
    public void run() {
        System.out.println("Have a socket to connect!");
        while (true) {
            try {
                String message = input.readLine();
                if (!routerMessage(message)) break;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        output.println("Good bye!!!!");
        try {
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public boolean routerMessage(String message) {
        StringTokenizer token = new StringTokenizer(message, " ");
        if (token.countTokens() == 0) return true;

        if (message.equalsIgnoreCase("exit"))
            return false;

        if (token.countTokens() == 2 && methodEncrypt == null && token.nextToken().equalsIgnoreCase("method_encrypt")) {
            methodEncrypt = token.nextToken();
            System.out.println("Thuat toan chon la: " + methodEncrypt);
            output.println("ok");
        }

        if (!login) {
            switch (message) {
                case "login":
                    login();
                    break;
                case "register":
                    register();
                    break;
            }
        }

        switch (message.toLowerCase()) {
            case "leave":
                if (channel != null) {
                    channel.leave(this);
                    channel = null;
                }
                return joinChannel();
            default:
                if (channel != null) {
                    channel.sendMessage(clientSocketId, message);
                }
        }
        return true;
    }

    public boolean joinChannel() {
        while (channel == null) {
            output.print("Input id channel you want joint: ");
            output.flush();
            try {
                String message = input.readLine();
                if (message.equalsIgnoreCase("exit")) return false;
                Channel c = server.getChannelById(message);
                if (c != null) {
                    channel = c;
                    output.println("Join channel " + message + " success!");
                    channel.join(this);
                } else {
                    output.println("Channel not exit!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private User getUser() {
        try {
            output.print("Nhap tai khoan: ");
            output.flush();
            String username = input.readLine();
            output.print("Nhap mat khau: ");
            output.flush();
            String password = input.readLine();
            return new User(-1, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void login() {
        User request = getUser();
        if (request == null) return;
        User user = authenticationService.login(request.getUsername(), request.getPassword());
        if (user == null) {
            output.println("Tài khoản hoặc mật khẩu không đúng!");
        } else {
            output.println("Đăng nhập thành công!");
            login = true;
        }
    }

    private void register() {
        User request = getUser();
        if (request == null) return;
        try {
            output.print("Nhap lai mat khau: ");
            output.flush();
            String confirmPassword = input.readLine();
            if (!request.getPassword().equals(confirmPassword)) {
                output.println("Mật khẩu nhập lại không chính xác");
                return;
            }
            int user = authenticationService.register(request.getUsername(), request.getPassword());
            if (user == -1) {
                output.println("Tài khoản đã tồn tại trên hệ thống!");
            } else {
                output.println("Đăng ký thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}