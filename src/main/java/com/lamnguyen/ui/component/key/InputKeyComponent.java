/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:54 AM - 10/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.key;

import lombok.Getter;
import com.lamnguyen.security.symmetrical.ISymmetrical;
import com.lamnguyen.security.symmetrical.SymmetricalKey;
import com.lamnguyen.ui.controller.DropFileAdapter;
import com.lamnguyen.ui.controller.KeyComponentController;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class InputKeyComponent extends JPanel implements Observer {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;
    private JTextField jtfInputKey;
    @Getter
    private JButton jbtSelectFileKey;
    private KeyComponentController keyComponentController;
    @Getter
    private Function<File, Void> onFileChanged;

    public InputKeyComponent(Function<File, Void> onFileChanged) {
        this.onFileChanged = onFileChanged;
        this.setOpaque(false);
        this.init();
    }

    private void init() {
        keyComponentController = new KeyComponentController(this);

        this.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        jtfInputKey = new JTextField() {{
            setPreferredSize(new Dimension(900, 50));
            setEditable(false);
            setEnabled(false);
        }};
        jtfInputKey.setDropTarget(new DropTarget(jtfInputKey, new DropFileAdapter(onFileChanged)));
        this.add(jtfInputKey);

        jbtSelectFileKey = new JButton("Load file key!") {{
            setPreferredSize(new Dimension(200, 50));
            addActionListener(keyComponentController);
        }};
        this.add(jbtSelectFileKey);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.white);
        g2.fillRoundRect(0, 0, getWidth() - STROKE_WIDTH, getHeight() - STROKE_WIDTH, RADIUS, RADIUS);

        var heightTitle = 10;
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(STROKE_WIDTH));
        g2.drawRoundRect(0, heightTitle, getWidth() - STROKE_WIDTH, getHeight() - STROKE_WIDTH - heightTitle, RADIUS, RADIUS);

        g2.setColor(Color.white);
        g2.fillRect(20, 0, 140, 28);

        g2.setColor(Color.BLACK);
        g2.drawString("Nhập File Key!", 30, 15);
    }

    @Override
    public void update(Observable observable, Object o) {
        var parentWidth = getParent().getWidth();
        this.setPreferredSize(new Dimension(parentWidth - 200, 110));
        this.setSize(this.getPreferredSize());
        jtfInputKey.setPreferredSize(new Dimension(parentWidth - 510, 50));
        this.updateUI();
        this.repaint();
    }

    public void setPathFileKey(String absolutePath) {
        jtfInputKey.setText(absolutePath);
    }
}
