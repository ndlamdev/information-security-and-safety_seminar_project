/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:29 AM - 10/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.component.algorithm;

import javax.swing.*;
import java.awt.*;

public class SelectAlgorithmComponent extends JPanel {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;


    public SelectAlgorithmComponent() {
        this.setOpaque(false);
        this.init();
    }

    private void init() {
        this.setPreferredSize(new Dimension(1500, 160));
        this.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        var algorithms = new String[]{"RSA", "DES", "AES"};
        var selectAlgorithm = new JComboBox<String>(algorithms) {{
            setPreferredSize(new Dimension(300, 50));
        }};

        var panelAlgorithm = new JPanel() {{
            setPreferredSize(new Dimension(450, 90));
            add(new JLabel("Chọn thuật toán!") {{
                setPreferredSize(new Dimension(300, 30));
            }});
            add(selectAlgorithm);
        }};

        this.add(panelAlgorithm);


        var modes = new String[]{""};
        var selectMode = new JComboBox<String>(modes) {{
            setPreferredSize(new Dimension(300, 50));
        }};

        var panelMode = new JPanel() {{
            setPreferredSize(new Dimension(450, 90));
            add(new JLabel("Chọn mode thuật toán!") {{
                setPreferredSize(new Dimension(300, 30));
            }});
            add(selectMode);
        }};

        this.add(panelMode);

        var paddings = new String[]{""};
        var selectPadding = new JComboBox<String>(paddings) {{
            setPreferredSize(new Dimension(300, 50));
        }};

        var panelPadding = new JPanel() {{
            setPreferredSize(new Dimension(450, 90));
            add(new JLabel("Chọn padding thuật toán!") {{
                setPreferredSize(new Dimension(300, 30));
            }});
            add(selectPadding);
        }};

        this.add(panelPadding);
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
        g2.fillRect(20, 0, 230, 28);

        g2.setColor(Color.BLACK);
        g2.drawString("Lựa chọn thuật toán!", 30, 18);
    }
}
