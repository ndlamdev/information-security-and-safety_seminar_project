/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:25 PM - 22/10/2024
 *  User: lam-nguyen
 **/

package main.java.service;

import main.java.model.User;

public interface IAuthenticationService {
    User login(String username, String password);

    int register(String username, String password);
}