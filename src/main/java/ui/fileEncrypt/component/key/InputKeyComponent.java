/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:54 AM - 10/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.component.key;

import lombok.Getter;
import main.java.helper.FileChooseHelper;
import main.java.ui.fileEncrypt.controller.KeyComponentController;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class InputKeyComponent extends JPanel {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;
    private JTextField jtfInputKey;
    @Getter
    private JButton jbtSelectFileKey;
    private KeyComponentController keyComponentController;

    public InputKeyComponent() {
        this.setOpaque(false);
        this.init();
    }

    private void init() {
        keyComponentController = new KeyComponentController(this);

        this.setPreferredSize(new Dimension(1500, 110));

        this.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        jtfInputKey = new JTextField() {{
            setPreferredSize(new Dimension(1200, 50));
        }};
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
        g2.fillRect(20, 0, 130, 28);

        g2.setColor(Color.BLACK);
        g2.drawString("Nhập File Key!", 30, 18);
    }

    public void loadFileKey() throws IOException {
        var file = FileChooseHelper.selectFile(FileChooseHelper.SelectFileMode.FILES_ONLY);
        if (file == null) return;


    }
}
