/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:29 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.socket;

import main.java.security.symmetrical.encrypt.AESEncrypt;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final ServerSocket serverSocket;
    private final List<Channel> channels;

    public Server() throws Exception {
        this.serverSocket = new ServerSocket(1305);
        this.channels = new ArrayList<>();
    }

    public void start() {
        new AESEncrypt();

        for (int i = 1; i <= 10; i++) {
            channels.add(new Channel(String.valueOf(i)));
        }

        while (true) {
            try {
                new ClientSocket(this, serverSocket.accept()).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Channel getChannelById(String id) {
        return channels.stream()
                .filter(channel -> channel.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}