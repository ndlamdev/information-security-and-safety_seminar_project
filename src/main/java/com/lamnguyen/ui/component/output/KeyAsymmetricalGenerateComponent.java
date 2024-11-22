/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:41 AM - 11/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.output;

import com.lamnguyen.helper.ClipboardHelper;
import com.lamnguyen.ui.component.label.LabelBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class KeyAsymmetricalGenerateComponent extends JPanel implements Observer {
    private final int STROKE_WIDTH = 2;
    private final int RADIUS = 10;
    private LabelBorder labelShowPublicKey, labelShowPrivateKey;
    private JLabel labelPublicKey, labelPrivateKey;

    public KeyAsymmetricalGenerateComponent() {
        this.setOpaque(false);
        this.init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(24, 20, 20, 20));

        labelPublicKey = new JLabel("Khóa công khai!");
        this.add(labelPublicKey);

        labelShowPublicKey = new LabelBorder() {{
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (labelShowPublicKey.getText().isBlank()) return;
                    ClipboardHelper.copy(labelShowPublicKey.getText(), "Copy khóa công khai thành công!");
                }
            });
        }};
        this.add(labelShowPublicKey);


        labelPrivateKey = new JLabel("Khóa riêng tư!");
        this.add(labelPrivateKey);

        labelShowPrivateKey = new LabelBorder() {{
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (labelShowPrivateKey.getText().isBlank()) return;
                    ClipboardHelper.copy(labelShowPrivateKey.getText(), "Copy khóa riêng tư thành công!");
                }
            });
        }};
        this.add(labelShowPrivateKey);
    }

    public void setPrivateKey(String key) {
        this.labelShowPrivateKey.setText(key);
        this.labelShowPrivateKey.repaint();
    }

    public void setPublicKey(String key) {
        this.labelShowPublicKey.setText(key);
        this.labelShowPublicKey.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.white);
        g2.fillRoundRect(0, 0, getWidth() - STROKE_WIDTH, getHeight() - STROKE_WIDTH, RADIUS, RADIUS);

        var heightTitle = 10;
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(STROKE_WIDTH));
        g2.drawRoundRect(0, heightTitle, getWidth() - STROKE_WIDTH, getHeight() - STROKE_WIDTH - heightTitle, RADIUS, RADIUS);

        g2.setColor(Color.white);
        g2.fillRect(20, 0, 130, 28);
        g2.setColor(Color.BLACK);
        g2.drawString("Key generate!", 30, 15);

        super.paintComponent(g);
    }

    @Override
    public void update(Observable observable, Object o) {
        var parentSize = getParent().getWidth();
        this.setPreferredSize(new Dimension(parentSize - 200, 230));
        labelShowPublicKey.setPreferredSize(new Dimension(parentSize - 300, 50));
        labelShowPrivateKey.setPreferredSize(new Dimension(parentSize - 300, 50));

        labelPublicKey.setPreferredSize(new Dimension(parentSize - 300, 30));
        labelPrivateKey.setPreferredSize(new Dimension(parentSize - 300, 30));
    }
}
