/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:36 AM - 12/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.controller;

import lombok.Getter;

import java.util.Observable;

public class SubjectSizeController extends Observable {
    public static final int WIDTH = 1800, HEIGHT = 1100;
    private final Size size;
    private static SubjectSizeController instance;

    private SubjectSizeController() {
        size = new Size();
    }

    public static SubjectSizeController getInstance() {
        if (instance == null) instance = new SubjectSizeController();
        return instance;
    }

    public void onChange() {
        setChanged();
        notifyObservers(size);
    }

    @Getter
    public static class Size {
        private int widthRight, widthLeft;
    }
}
