/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:01 AM - 11/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.component.algorithm;

import lombok.Getter;
import lombok.Setter;
import main.java.config.KeyConfig;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;
import java.util.function.Function;

public class SelectAlgorithmCipherSymmetricalComponent extends JPanel {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;
    private JComboBox<String> jbcAlgorithms, jbcSizeKeyAlgorithms;
    private Map<String, List<String>> mapAlgorithms;
    private Function<AlgorithmKey, Void> onAlgorithmKeyChanged;

    public SelectAlgorithmCipherSymmetricalComponent(Function<AlgorithmKey, Void> onAlgorithmKeyChanged) {
        this.onAlgorithmKeyChanged = onAlgorithmKeyChanged;
        this.setOpaque(false);

        mapAlgorithms = KeyConfig.getInstance().getMapAlgorithmSymmetrical();

        this.init();
    }

    private void init() {
        this.setPreferredSize(new Dimension(1500, 150));
        this.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        var dimensionPanel = new Dimension(720, 90);
        var dimensionComboBox = new Dimension(700, 50);
        var dimensionLabel = new Dimension(700, 30);

        jbcAlgorithms = new JComboBox<>(mapAlgorithms.keySet().toArray(String[]::new)) {{
            setPreferredSize(dimensionComboBox);
            addActionListener(itemEvent -> {
                var algorithm = (String) jbcAlgorithms.getSelectedItem();
                jbcSizeKeyAlgorithms.setModel(new DefaultComboBoxModel<>(mapAlgorithms.get(algorithm).toArray(String[]::new)));
                onAlgorithmKeyChanged.apply(getAlgorithmKey());
            });
        }};

        this.add(new JPanel() {{
            setPreferredSize(dimensionPanel);
            add(new JLabel("Thuật toán!") {{
                setPreferredSize(dimensionLabel);
            }});
            add(jbcAlgorithms);
        }});


        jbcSizeKeyAlgorithms = new JComboBox<>(mapAlgorithms.get(mapAlgorithms.keySet().iterator().next()).toArray(String[]::new)) {{
            setPreferredSize(dimensionComboBox);
            addActionListener(actionEvent -> {
                onAlgorithmKeyChanged.apply(getAlgorithmKey());
            });
        }};

        this.add(new JPanel() {{
            setPreferredSize(dimensionPanel);
            add(new JLabel("Kích thước khóa!") {{
                setPreferredSize(dimensionLabel);
            }});
            add(jbcSizeKeyAlgorithms);
        }});
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

    public AlgorithmKey getAlgorithmKey() {
        return new AlgorithmKey((String) jbcAlgorithms.getSelectedItem(), Integer.parseInt((String) jbcSizeKeyAlgorithms.getSelectedItem()));
    }

    @Getter
    @Setter
    public static class AlgorithmKey {
        private String name;
        private int size;

        public AlgorithmKey(String name, int size) {
            this.name = name;
            this.size = size;
        }
    }
}
