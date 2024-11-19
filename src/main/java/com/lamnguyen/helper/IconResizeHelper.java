/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:38 AM - 12/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.helper;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class IconResizeHelper {
    private static IconResizeHelper instance;

    private IconResizeHelper() {

    }

    public static IconResizeHelper getInstance() {
        if (instance == null) instance = new IconResizeHelper();
        return instance;
    }

    public ImageIcon initImageIcon(String path, int width, int height) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(IconResizeHelper.class.getClassLoader().getResourceAsStream(path).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
