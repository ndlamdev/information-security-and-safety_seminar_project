/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:33 AM - 15/10/2024
 * User: lam-nguyen
 **/

package main.java.socket;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel {
    @Getter
    private final String id;
    private final List<ClientSocket> clients;

    public Channel() {
        this.id = UUID.randomUUID().toString();
        this.clients = new ArrayList<>();
    }

    public Channel(String id) {
        this.id = id;
        this.clients = new ArrayList<>();
    }

    public void join(ClientSocket client) {
        clients.add(client);
        sendMessage(client.getClientSocketId(), "Have client " + client.getClientSocketId() + " join this channel");
    }

    public void leave(ClientSocket client) {
        clients.remove(client);
        sendMessage(client.getClientSocketId(), "Have client " + client.getClientSocketId() + " leave this channel");
    }

    public void sendMessage(String idSender, String message) {
        for (ClientSocket client : clients) {
            if (!client.getClientSocketId().equals(idSender)) {
                client.sendMessage(message);
            }
        }
    }
}