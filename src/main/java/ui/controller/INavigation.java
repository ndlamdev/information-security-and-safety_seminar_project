/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:00 AM - 29/10/2024
 * User: lam-nguyen
 **/

package main.java.ui.controller;

public interface INavigation {
    void push(NamePage name);

    enum NamePage {
        AlgorithmPage, ChatPage, WelcomePage, LoginPage
    }
}