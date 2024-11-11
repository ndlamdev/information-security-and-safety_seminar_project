/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Created at: 9:01 AM - 29/10/2024
 * User: lam-nguyen
 */

package main.java.ui.fileEncrypt.controller.navigation.impl;

import main.java.ui.fileEncrypt.controller.navigation.IJNavigation;

import javax.swing.*;
import java.awt.*;

public class JNavigation implements IJNavigation {
    private final CardLayout layout;
    private final JPanel container;
    private NamePage currentPage;

    public JNavigation(CardLayout layout, JPanel container) {
        this.layout = layout;
        this.container = container;
    }

    @Override
    public void push(NamePage name) {
        if (currentPage == name) return;
        this.currentPage = name;
        layout.show(container, currentPage.name());
    }
}
