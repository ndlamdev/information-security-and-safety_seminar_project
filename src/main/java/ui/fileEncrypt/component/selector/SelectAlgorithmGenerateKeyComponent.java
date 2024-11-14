/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:01 AM - 11/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.component.selector;

import lombok.Getter;
import lombok.Setter;
import main.java.config.KeyConfig;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class SelectAlgorithmGenerateKeyComponent extends JPanel implements Observer {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;
    private JComboBox<String> jbcAlgorithms, jbcSizeKey;
    private Map<String, List<String>> mapAlgorithms;
    private Function<AlgorithmKey, Void> onAlgorithmKeyChanged;
    private Dimension dimensionPanel, dimensionComboBox, dimensionLabel;
    private JLabel labelSizeKey;
    private JPanel panelSizeKey;
    private JLabel labelAlgtithm;
    private JPanel panelAlgorithm;

    public SelectAlgorithmGenerateKeyComponent(Function<AlgorithmKey, Void> onAlgorithmKeyChanged, Map<String, List<String>> mapAlgorithms) {
        this.onAlgorithmKeyChanged = onAlgorithmKeyChanged;
        this.setOpaque(false);

        this.mapAlgorithms = mapAlgorithms;

        this.init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        dimensionPanel = new Dimension(0, 90);
        dimensionComboBox = new Dimension(0, 50);
        dimensionLabel = new Dimension(0, 30);

        jbcAlgorithms = new JComboBox<>(mapAlgorithms.keySet().toArray(String[]::new)) {{
            addActionListener(itemEvent -> {
                var algorithm = (String) jbcAlgorithms.getSelectedItem();
                jbcSizeKey.setModel(new DefaultComboBoxModel<>(mapAlgorithms.get(algorithm).toArray(String[]::new)));
                onAlgorithmKeyChanged.apply(getAlgorithmKey());
            });
        }};

        labelAlgtithm = new JLabel("Thuật toán!");
        panelAlgorithm = new JPanel() {{
            add(labelAlgtithm);
            add(jbcAlgorithms);
            setOpaque(false);
        }};
        this.add(panelAlgorithm);


        jbcSizeKey = new JComboBox<>(mapAlgorithms.get(mapAlgorithms.keySet().iterator().next()).toArray(String[]::new)) {{
            addActionListener(actionEvent -> {
                onAlgorithmKeyChanged.apply(getAlgorithmKey());
            });
        }};

        labelSizeKey = new JLabel("Kích thước khóa!");
        panelSizeKey = new JPanel() {{
            add(labelSizeKey);
            setOpaque(false);
            add(jbcSizeKey);
        }};
        this.add(panelSizeKey);
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
        g2.fillRect(20, 0, 190, 28);

        g2.setColor(Color.BLACK);
        g2.drawString("Lựa chọn thuật toán!", 30, 18);
    }

    public AlgorithmKey getAlgorithmKey() {
        return new AlgorithmKey((String) jbcAlgorithms.getSelectedItem(), Integer.parseInt((String) jbcSizeKey.getSelectedItem()));
    }

    @Override
    public void update(Observable observable, Object o) {
        var parentSize = getParent().getWidth();
        this.setPreferredSize(new Dimension(parentSize - 200, 150));

        dimensionPanel.width = (parentSize - 260) / 2;
        panelAlgorithm.setPreferredSize(dimensionPanel);
        panelSizeKey.setPreferredSize(dimensionPanel);

        dimensionLabel.width = dimensionPanel.width - 20;
        labelAlgtithm.setPreferredSize(dimensionLabel);
        labelSizeKey.setPreferredSize(dimensionLabel);


        dimensionComboBox.width = dimensionLabel.width;
        jbcAlgorithms.setPreferredSize(dimensionComboBox);
        jbcSizeKey.setPreferredSize(dimensionComboBox);
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
