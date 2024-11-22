/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:29 AM - 10/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.selector;

import com.lamnguyen.config.CipherAlgorithmConfig;
import com.lamnguyen.security.symmetrical.ISymmetrical;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.List;
import java.util.function.Function;

public class SelectCipherAlgorithmComponent extends JPanel implements Observer {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;
    private final List<CipherAlgorithmConfig.Algorithm> algorithmList;
    private HeightItemComponent heightItemComponent;
    private JComboBox<String> jcbAlgorithm, jcbMode, jcbPadding;
    private Function<Algorithm, Void> onAlgorithmChanged;
    private JPanel panelAlgorithm, panelMode, panelPadding;
    private Dimension sizePanel, sizeLabel, sizeCombobox;
    private JLabel labelAlgorithm, labelMode, labelPadding;
    private String title = "Lựa chọn thuật toán!";

    public record HeightItemComponent(int labelHeight, int comboBoxHeight, int padding) {
    }

    public SelectCipherAlgorithmComponent(List<CipherAlgorithmConfig.Algorithm> algorithmList, Function<Algorithm, Void> onAlgorithmChanged) {
        this.heightItemComponent = new HeightItemComponent(30, 50, 20);
        this.algorithmList = algorithmList;
        this.onAlgorithmChanged = onAlgorithmChanged;
        this.setOpaque(false);
        this.init();
        this.event();
    }

    public SelectCipherAlgorithmComponent(List<CipherAlgorithmConfig.Algorithm> algorithmList) {
        this.algorithmList = algorithmList;
        this.heightItemComponent = new HeightItemComponent(30, 50, 20);
        this.setOpaque(false);
        this.init();
        this.event();
    }

    public SelectCipherAlgorithmComponent(List<CipherAlgorithmConfig.Algorithm> algorithmList, Function<Algorithm, Void> onAlgorithmChanged, HeightItemComponent heightItemComponent) {
        this.heightItemComponent = heightItemComponent;
        this.algorithmList = algorithmList;
        this.onAlgorithmChanged = onAlgorithmChanged;
        this.setOpaque(false);
        this.init();
        this.event();
    }

    public SelectCipherAlgorithmComponent(List<CipherAlgorithmConfig.Algorithm> algorithmList, HeightItemComponent heightItemComponent) {
        this.heightItemComponent = heightItemComponent;
        this.algorithmList = algorithmList;
        this.setOpaque(false);
        this.init();
        this.event();
    }

    private void event() {
    }

    private void init() {
        sizeLabel = new Dimension(0, heightItemComponent.labelHeight);
        sizeCombobox = new Dimension(0, heightItemComponent.comboBoxHeight);
        sizePanel = new Dimension(0, heightItemComponent.labelHeight + heightItemComponent.comboBoxHeight + 10);

        this.setBorder(BorderFactory.createEmptyBorder(heightItemComponent.padding, heightItemComponent.padding, heightItemComponent.padding, heightItemComponent.padding));

        var algorithms = algorithmList.stream().map(CipherAlgorithmConfig.Algorithm::alg).toArray(String[]::new);
        jcbAlgorithm = new JComboBox<>(algorithms) {{
            addActionListener(actionEvent -> {
                var alg = (String) jcbAlgorithm.getSelectedItem();
                var optional = algorithmList.stream().filter(it -> it.alg().equals(alg)).findFirst();
                if (optional.isEmpty()) return;
                jcbPadding.setModel(new DefaultComboBoxModel<>(optional.get().paddings().toArray(String[]::new)));
                jcbMode.setModel(new DefaultComboBoxModel<>(optional.get().modes().toArray(String[]::new)));
                if (onAlgorithmChanged != null)
                    onAlgorithmChanged.apply(getAlgorithm());
            });
        }};
        labelAlgorithm = new JLabel("Chọn thuật toán!");
        panelAlgorithm = new JPanel() {{
            add(labelAlgorithm);
            add(jcbAlgorithm);
            setOpaque(false);
        }};
        this.add(panelAlgorithm);

        var defaultAlg = algorithmList.get(0);
        jcbMode = new JComboBox<>(defaultAlg.modes().toArray(String[]::new)) {{
            addActionListener(actionEvent -> {
                var mode = (String) jcbMode.getSelectedItem();
                if (mode != null && mode.isEmpty())
                    jcbPadding.setSelectedItem("");
                if (onAlgorithmChanged != null)
                    onAlgorithmChanged.apply(getAlgorithm());
            });
        }};
        labelMode = new JLabel("Chọn mode thuật toán!");
        panelMode = new JPanel() {{
            add(labelMode);
            add(jcbMode);
            setOpaque(false);
        }};
        this.add(panelMode);

        jcbPadding = new JComboBox<>(defaultAlg.paddings().toArray(String[]::new)) {{
            addActionListener(actionEvent -> {
                var padding = (String) jcbPadding.getSelectedItem();
                if (padding != null && padding.isEmpty())
                    jcbMode.setSelectedItem("");
                if (onAlgorithmChanged != null)
                    onAlgorithmChanged.apply(getAlgorithm());
            });
        }};
        labelPadding = new JLabel("Chọn padding thuật toán!");
        panelPadding = new JPanel() {{
            add(labelPadding);
            add(jcbPadding);
            setOpaque(false);
        }};
        this.add(panelPadding);
    }

    public Algorithm getAlgorithm() {
        var alg = (String) jcbAlgorithm.getSelectedItem();
        var padding = jcbPadding.getSelectedItem();
        var mode = jcbMode.getSelectedItem();
        return new Algorithm(alg, mode == null || ((String) mode).isEmpty() ? null : (String) mode, padding == null || ((String) padding).isEmpty() ? null : (String) padding);
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
        g2.fillRect(20, 0, (int) (title.length() * 5.7) + 20, 28);

        g2.setColor(Color.BLACK);
        g2.drawString(title, 30, 18);
    }

    public int getHeight() {
        return sizePanel.height + 10 + heightItemComponent.padding * 2;
    }

    @Override
    public void update(Observable observable, Object o) {
        var sizeParent = getParent().getWidth();
        this.setPreferredSize(new Dimension(sizeParent - 200, getHeight()));
        this.setSize(this.getPreferredSize());

        sizePanel.width = (sizeParent - 270) / 3;
        panelAlgorithm.setPreferredSize(sizePanel);
        panelMode.setPreferredSize(sizePanel);
        panelPadding.setPreferredSize(sizePanel);

        sizeLabel.width = sizePanel.width - 10;
        labelAlgorithm.setPreferredSize(sizeLabel);
        labelMode.setPreferredSize(sizeLabel);
        labelPadding.setPreferredSize(sizeLabel);

        sizeCombobox.width = sizeLabel.width;
        jcbAlgorithm.setPreferredSize(sizeCombobox);
        jcbMode.setPreferredSize(sizeCombobox);
        jcbPadding.setPreferredSize(sizeCombobox);

        this.updateUI();
        this.repaint();
    }

    public void setTitle(String title) {
        this.title = title;
        this.updateUI();
        this.repaint();
    }

    public void setHeightItemComponent(HeightItemComponent heightItemComponent) {
        this.heightItemComponent = heightItemComponent;
        sizeLabel = new Dimension(0, heightItemComponent.labelHeight);
        sizeCombobox = new Dimension(0, heightItemComponent.comboBoxHeight);
        sizePanel = new Dimension(0, heightItemComponent.labelHeight + heightItemComponent.comboBoxHeight + 10);

        var sizeParent = getParent().getWidth();
        this.setPreferredSize(new Dimension(sizeParent - 200, getHeight()));
        this.setSize(this.getPreferredSize());

        sizePanel.width = (sizeParent - 270) / 3;
        panelAlgorithm.setPreferredSize(sizePanel);
        panelMode.setPreferredSize(sizePanel);
        panelPadding.setPreferredSize(sizePanel);

        sizeLabel.width = sizePanel.width - 10;
        labelAlgorithm.setPreferredSize(sizeLabel);
        labelMode.setPreferredSize(sizeLabel);
        labelPadding.setPreferredSize(sizeLabel);

        sizeCombobox.width = sizeLabel.width;
        jcbAlgorithm.setPreferredSize(sizeCombobox);
        jcbMode.setPreferredSize(sizeCombobox);
        jcbPadding.setPreferredSize(sizeCombobox);

        this.updateUI();
        this.repaint();
    }

    public record Algorithm(String algorithm, String mode, String padding) {
    }
}
