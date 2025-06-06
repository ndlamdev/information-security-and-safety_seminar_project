/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:18 PM - 10/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.input;

import com.lamnguyen.helper.FileChooseHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectFolderDest extends JLabel {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;
    private String fileDest;

    public SelectFolderDest() {
        this.setOpaque(false);
        this.init();
        this.event();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.white);
        g2.fillRoundRect(0, 0, getWidth() - STROKE_WIDTH, getHeight() - STROKE_WIDTH, RADIUS, RADIUS);

        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(STROKE_WIDTH));
        g2.drawRoundRect(0, 0, getWidth() - STROKE_WIDTH, getHeight() - STROKE_WIDTH, RADIUS, RADIUS);

        super.paintComponent(g);
    }

    private void event() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var file = FileChooseHelper.selectFile(FileChooseHelper.SelectFileMode.DIRECTORIES_ONLY);
                if (file == null) return;
                fileDest = file.getAbsolutePath();
                setText(fileDest);
            }
        });
    }
}
