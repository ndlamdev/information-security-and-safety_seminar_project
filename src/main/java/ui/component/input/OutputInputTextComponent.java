/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:53 AM - 16/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.component.input;

import main.java.helper.ClipboardHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class OutputInputTextComponent extends JPanel implements Observer {
    private final int STROKE_WIDTH = 2;
    private final int RADIUS = 10;
    private JTextArea jTextArea;
    private JLabel label;
    private String textLabel;
    private JScrollPane scrollPane;

    public OutputInputTextComponent(String textLabel) {
        this.textLabel = textLabel;
        this.setOpaque(false);
        this.init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        jTextArea = new JTextArea() {{
            setLineWrap(true);
            setWrapStyleWord(true);
        }};
        scrollPane = new JScrollPane(jTextArea);
        this.add(scrollPane);
    }

    public void setLabel(String text) {
        this.label.setText(text);
    }

    public void setEditable(Boolean value) {
        this.jTextArea.setEditable(value);
        this.jTextArea.setBackground(Color.white);
    }

    public void setTextJTextArea(String text) {
        this.jTextArea.setText(text);
    }

    public String getText() {
        return this.jTextArea.getText();
    }

    public void clickToCopy(boolean active) {
        if (!active) return;
        jTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jTextArea.getText().isBlank()) return;
                ClipboardHelper.copy(jTextArea.getText(), "Copy vãn bản mã hóa thành công!");
            }
        });
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
        g2.fillRect(20, 0, textLabel.length() * 10 + 15, 28);
        g2.setColor(Color.BLACK);
        g2.drawString(textLabel, 30, 18);

        super.paintComponent(g);
    }

    public void setSize(Dimension size) {
        this.setPreferredSize(new Dimension(size.width, size.height));
        this.scrollPane.setPreferredSize(new Dimension(size.width - 40, size.height - 40));
    }

    @Override
    public void update(Observable observable, Object o) {
        var parentSize = getParent().getWidth();
        this.setPreferredSize(new Dimension(parentSize - 200, 180));
        this.scrollPane.setPreferredSize(new Dimension(parentSize - 200 - 40, 140));
    }
}
