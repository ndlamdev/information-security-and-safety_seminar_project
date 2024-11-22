/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:29 AM - 10/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.selector;

import com.lamnguyen.config.SignatureAlgorithmConfig;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class SelectSignatureAlgorithmComponent extends JPanel implements Observer {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;
    private final List<SignatureAlgorithmConfig.Algorithm> algorithmList;
    private JComboBox<String> jcbAlgorithm, jcbIn, jcbHashAlgorithm;
    private Function<String, Void> onAlgorithmChanged;
    private JPanel panelAlgorithm, panelMode, panelHashAlgorithm;
    private Dimension sizePanel, sizeLabel, sizeCombobox;
    private JLabel labelAlgorithm, labelIn, labelHashAlgorithm;

    public SelectSignatureAlgorithmComponent(List<SignatureAlgorithmConfig.Algorithm> algorithmList, Function<String, Void> onAlgorithmChanged) {
        this.algorithmList = algorithmList;
        this.onAlgorithmChanged = onAlgorithmChanged;
        this.setOpaque(false);
        this.init();
        this.event();
    }

    public SelectSignatureAlgorithmComponent(List<SignatureAlgorithmConfig.Algorithm> algorithmList) {
        this.algorithmList = algorithmList;
        this.setOpaque(false);
        this.init();
        this.event();
    }

    private void event() {
    }

    private void init() {
        sizePanel = new Dimension(0, 90);
        sizeLabel = new Dimension(0, 20);
        sizeCombobox = new Dimension(0, 50);

        this.setBorder(BorderFactory.createEmptyBorder(25, 20, 20, 20));

        var algorithms = algorithmList.stream().map(SignatureAlgorithmConfig.Algorithm::alg).toArray(String[]::new);
        jcbAlgorithm = new JComboBox<>(algorithms) {{
            addActionListener(actionEvent -> {
                var alg = (String) jcbAlgorithm.getSelectedItem();
                var optional = algorithmList.stream().filter(it -> it.alg().equals(alg)).findFirst();
                if (optional.isEmpty()) return;
                jcbHashAlgorithm.setModel(new DefaultComboBoxModel<>(optional.get().hashAlgorithms().toArray(String[]::new)));
                jcbIn.setModel(new DefaultComboBoxModel<>(optional.get().ins().toArray(String[]::new)));
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
        jcbHashAlgorithm = new JComboBox<>(defaultAlg.hashAlgorithms().toArray(String[]::new)) {{
            addActionListener(actionEvent -> {
                var padding = (String) jcbHashAlgorithm.getSelectedItem();
                if (padding != null && padding.isEmpty())
                    jcbIn.setSelectedItem("");
                if (onAlgorithmChanged != null)
                    onAlgorithmChanged.apply(getAlgorithm());
            });
        }};
        labelHashAlgorithm = new JLabel("Chọn thuật toán hash file!");
        panelHashAlgorithm = new JPanel() {{
            add(labelHashAlgorithm);
            add(jcbHashAlgorithm);
            setOpaque(false);
        }};
        this.add(panelHashAlgorithm);

        jcbIn = new JComboBox<>(defaultAlg.ins().toArray(String[]::new)) {{
            addActionListener(actionEvent -> {
                var mode = (String) jcbIn.getSelectedItem();
                if (mode != null && mode.isEmpty())
                    jcbHashAlgorithm.setSelectedItem("");
                if (onAlgorithmChanged != null)
                    onAlgorithmChanged.apply(getAlgorithm());
            });
        }};
        labelIn = new JLabel("Chọn kiểu format");
        panelMode = new JPanel() {{
            add(labelIn);
            add(jcbIn);
            setOpaque(false);
        }};
        this.add(panelMode);
    }

    public String getAlgorithm() {
        var alg = (String) jcbAlgorithm.getSelectedItem();
        var hashAlgorithm = (String) jcbHashAlgorithm.getSelectedItem();
        var in = jcbIn.getSelectedItem();

        var builder = new StringBuilder(hashAlgorithm).append("with").append(alg);
        if (in == null || in.toString().isBlank()) return builder.toString();
        return builder.append("in").append(in).toString();
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
        g2.drawString("Lựa chọn thuật toán!", 30, 15);
    }

    @Override
    public void update(Observable observable, Object o) {
        var sizeParent = getParent().getWidth();
        this.setPreferredSize(new Dimension(sizeParent - 200, 150));

        sizePanel.width = (sizeParent - 270) / 3;
        panelAlgorithm.setPreferredSize(sizePanel);
        panelMode.setPreferredSize(sizePanel);
        panelHashAlgorithm.setPreferredSize(sizePanel);

        sizeLabel.width = sizePanel.width - 10;
        labelAlgorithm.setPreferredSize(sizeLabel);
        labelIn.setPreferredSize(sizeLabel);
        labelHashAlgorithm.setPreferredSize(sizeLabel);

        sizeCombobox.width = sizeLabel.width;
        jcbAlgorithm.setPreferredSize(sizeCombobox);
        jcbIn.setPreferredSize(sizeCombobox);
        jcbHashAlgorithm.setPreferredSize(sizeCombobox);
    }
}
