/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:52 PM - 10/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.component.label;

import javax.swing.*;
import java.awt.*;

public class LabelBorder extends JLabel {
    private final int STROKE_WIDTH = 2;

    public LabelBorder(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        this.init();
    }

    public LabelBorder(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.init();
    }

    public LabelBorder(String text) {
        super(text);
        this.init();
    }

    public LabelBorder(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        this.init();
    }

    public LabelBorder(Icon image) {
        super(image);
        this.init();
    }

    public LabelBorder() {
        this.init();
    }

    private void init() {
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.white);
        g2.fillRoundRect(0, 0, getWidth() - STROKE_WIDTH, getHeight() - STROKE_WIDTH, 10, 10);

        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(STROKE_WIDTH));
        g2.drawRoundRect(0, 0, getWidth() - STROKE_WIDTH, getHeight() - STROKE_WIDTH, 10, 10);

        super.paintComponent(g);
    }

}
