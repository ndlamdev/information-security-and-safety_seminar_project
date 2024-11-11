/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Created at: 9:01 AM - 29/10/2024
 * User: lam-nguyen
 */

package main.java.ui.chat.controller.impl;

import main.java.ui.chat.controller.INavigation;
import javax.swing.*;
import java.awt.*;

public class Navigation implements INavigation {
    private final CardLayout layout;
    private final JPanel container;

    public Navigation(CardLayout layout, JPanel container) {
        this.layout = layout;
        this.container = container;
    }

    @Override
    public void push(INavigation.NamePage name) {
        layout.show(container, name.name());
    }
}
