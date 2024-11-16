/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:41 AM - 11/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.component.key;

import main.java.helper.ClipboardHelper;
import main.java.ui.component.label.LabelBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class KeySymmetricalGenerateComponent extends JPanel implements Observer {
    private final int STROKE_WIDTH = 2;
    private final int RADIUS = 10;
    private LabelBorder labelKey;

    public KeySymmetricalGenerateComponent() {
        this.setOpaque(false);
        this.init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        labelKey = new LabelBorder() {{
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (labelKey.getText().isBlank()) return;
                    ClipboardHelper.copy(labelKey.getText(), "Copy khóa thành công!");
                }
            });
        }};
        this.add(labelKey);
    }

    public void setKey(String key) {
        this.labelKey.setText(key);
        this.labelKey.repaint();
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
        g2.drawString("Key generate!", 30, 18);

        super.paintComponent(g);
    }

    @Override
    public void update(Observable observable, Object o) {
        var parentSize = getParent().getWidth();
        this.setPreferredSize(new Dimension(parentSize - 200, 110));
        labelKey.setPreferredSize(new Dimension(parentSize - 300, 50));
    }
}
