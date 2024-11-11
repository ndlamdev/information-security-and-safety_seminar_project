/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:18 PM - 22/10/2024
 *  User: lam-nguyen
 **/

package main.java.service.impl;

import main.java.model.User;
import main.java.service.IAuthenticationService;

import java.security.SecureRandom;
import java.util.Vector;

public class AuthenticationServiceImpl implements IAuthenticationService {
    private final Vector<User> users = new Vector<>();

    public User findByUsername(String username) {
        return users.stream()
            .filter(user -> user.getUsername().equals(username))
        .findFirst()
        .orElse(null);
    }

    @Override
    public int register(String username, String password) {
        if (findByUsername(username) != null) return -1;
        User user = new User(new SecureRandom().nextInt(), username, password);
        users.add(user);
        return user.getId();
    }

    @Override
    public User login(String username, String password) {
        return users.stream()
            .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
        .findFirst()
        .orElse(null);
    }
}