/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:01 AM - 11/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.selector;

import lombok.Getter;
import lombok.Setter;
import com.lamnguyen.config.KeyConfig;
import com.lamnguyen.model.traditionalCipher.ITraditionalCipher;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class SelectAlgorithmGenerateTraditionalKeyComponent extends JPanel implements Observer {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;
    private JComboBox<String> jbcAlgorithms, jbcSizeKeyHill;
    private final Map<String, List<String>> mapAlgorithms;
    private final Function<AlgorithmKey, Void> onAlgorithmKeyChanged;
    private Dimension dimensionPanel, dimensionComboBox, dimensionLabel;
    private JLabel labelSizeKey, labelAlgorithm;
    private JPanel panelSizeKey, panelAlgorithm, panelTypeInputSizeKey, panelInputSizeAffine;
    private CardLayout cardLayout;
    private JTextField inputSizeKeyShift, inputSizeKeyAAffine, inputSizeKeyBAffine, inputSizeKeyVigenere, inputSizeKeySubstitution;
    private AlgorithmKey key;
    private NumericFilter numericFilter;

    public SelectAlgorithmGenerateTraditionalKeyComponent(Function<AlgorithmKey, Void> onAlgorithmKeyChanged) {
        this.onAlgorithmKeyChanged = onAlgorithmKeyChanged;
        this.setOpaque(false);

        this.mapAlgorithms = KeyConfig.getInstance().getMapAlgorithmTraditional();

        this.init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(25, 20, 20, 20));
        numericFilter = new NumericFilter();

        dimensionPanel = new Dimension(0, 90);
        dimensionComboBox = new Dimension(0, 50);
        dimensionLabel = new Dimension(0, 20);

        jbcAlgorithms = new JComboBox<>(mapAlgorithms.keySet().toArray(String[]::new)) {{
            addActionListener(itemEvent -> {
                var algorithm = (String) jbcAlgorithms.getSelectedItem();
                cardLayout.show(panelTypeInputSizeKey, algorithm);
                notifyChangeAlgorithm(algorithm);
            });
        }};

        labelAlgorithm = new JLabel("Thuật toán!");
        panelAlgorithm = new JPanel() {{
            add(labelAlgorithm);
            add(jbcAlgorithms);
            setOpaque(false);
        }};
        this.add(panelAlgorithm);


        cardLayout = new CardLayout();
        panelTypeInputSizeKey = new JPanel(cardLayout) {{
            setOpaque(false);
        }};

        jbcSizeKeyHill = new JComboBox<>(new DefaultComboBoxModel<>(mapAlgorithms.get(ITraditionalCipher.Algorithms.HILL.name()).toArray(String[]::new))) {{
            addActionListener(actionEvent -> {
                key = new AlgorithmKey((String) jbcAlgorithms.getSelectedItem(), (String) jbcSizeKeyHill.getSelectedItem());
                onAlgorithmKeyChanged.apply(getAlgorithmKey());
            });
        }};
        panelTypeInputSizeKey.add(jbcSizeKeyHill, ITraditionalCipher.Algorithms.HILL.name());

        inputSizeKeyShift = new JTextField() {{
            ((AbstractDocument) getDocument()).setDocumentFilter(numericFilter);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    var size = inputSizeKeyShift.getText();
                    if (size.isBlank()) key = null;
                    else key = new AlgorithmKey((String) jbcAlgorithms.getSelectedItem(), size);
                    onAlgorithmKeyChanged.apply(key);
                }
            });
        }};
        panelTypeInputSizeKey.add(inputSizeKeyShift, ITraditionalCipher.Algorithms.SHIFT.name());

        inputSizeKeyVigenere = new JTextField() {{
            ((AbstractDocument) getDocument()).setDocumentFilter(numericFilter);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    var size = inputSizeKeyVigenere.getText();
                    if (size.isBlank()) key = null;
                    else key = new AlgorithmKey((String) jbcAlgorithms.getSelectedItem(), size);
                    onAlgorithmKeyChanged.apply(key);
                }
            });
        }};
        panelTypeInputSizeKey.add(inputSizeKeyVigenere, ITraditionalCipher.Algorithms.VIGENERE.name());

        inputSizeKeySubstitution = new JTextField() {{
            ((AbstractDocument) getDocument()).setDocumentFilter(numericFilter);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    var size = inputSizeKeySubstitution.getText();
                    if (size.isBlank()) key = null;
                    else key = new AlgorithmKey((String) jbcAlgorithms.getSelectedItem(), size);
                    onAlgorithmKeyChanged.apply(key);
                }
            });
        }};
        panelTypeInputSizeKey.add(inputSizeKeySubstitution, ITraditionalCipher.Algorithms.SUBSTITUTION.name());

        inputSizeKeyAAffine = new JTextField() {{
            ((AbstractDocument) getDocument()).setDocumentFilter(numericFilter);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    sizeKeyAffineChange();
                }
            });
        }};

        inputSizeKeyBAffine = new JTextField() {{
            ((AbstractDocument) getDocument()).setDocumentFilter(numericFilter);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    sizeKeyAffineChange();
                }
            });
        }};

        panelInputSizeAffine = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)) {{
            setOpaque(false);
            add(new JLabel("A") {{
                setPreferredSize(new Dimension(20, 50));
            }});
            add(inputSizeKeyAAffine);
            add(new JLabel("") {{
                setPreferredSize(new Dimension(50, 50));
            }});
            add(new JLabel("B") {{
                setPreferredSize(new Dimension(20, 50));
            }});
            add(inputSizeKeyBAffine);
        }};
        panelTypeInputSizeKey.add(panelInputSizeAffine, ITraditionalCipher.Algorithms.AFFINE.name());

        labelSizeKey = new JLabel("Kích thước khóa!");
        panelSizeKey = new JPanel() {{
            add(labelSizeKey);
            setOpaque(false);
            add(panelTypeInputSizeKey);
        }};
        this.add(panelSizeKey);

        key = new AlgorithmKey(ITraditionalCipher.Algorithms.HILL.name(), (String) jbcSizeKeyHill.getSelectedItem());
    }

    private void notifyChangeAlgorithm(String algorithm) {
        key = switch (ITraditionalCipher.Algorithms.valueOf(algorithm)) {
            case HILL -> {
                jbcSizeKeyHill.setSelectedIndex(0);
                yield new AlgorithmKey(algorithm, (String) jbcSizeKeyHill.getSelectedItem());
            }
            case AFFINE -> {
                inputSizeKeyAAffine.setText("10");
                inputSizeKeyBAffine.setText("10");
                var a = inputSizeKeyAAffine.getText();
                var b = inputSizeKeyBAffine.getText();
                yield new AlgorithmKey((String) jbcAlgorithms.getSelectedItem(), a + "_" + b);
            }
            case SHIFT -> {
                inputSizeKeyShift.setText("10");
                yield new AlgorithmKey(algorithm, inputSizeKeyShift.getText());
            }
            case SUBSTITUTION -> {
                inputSizeKeySubstitution.setText("2");
                yield new AlgorithmKey(algorithm, inputSizeKeySubstitution.getText());
            }
            case VIGENERE -> {
                inputSizeKeyVigenere.setText("10");
                yield new AlgorithmKey(algorithm, inputSizeKeyVigenere.getText());
            }
        };

        onAlgorithmKeyChanged.apply(key);
    }

    private void sizeKeyAffineChange() {
        var a = inputSizeKeyAAffine.getText();
        var b = inputSizeKeyBAffine.getText();
        if (a.isBlank() || b.isBlank()) key = null;
        else key = new AlgorithmKey((String) jbcAlgorithms.getSelectedItem(), a + "_" + b);
        onAlgorithmKeyChanged.apply(key);
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

    public AlgorithmKey getAlgorithmKey() {
        return key;
    }

    @Override
    public void update(Observable observable, Object o) {
        var parentSize = getParent().getWidth();
        this.setPreferredSize(new Dimension(parentSize - 200, 130));

        var size = (parentSize - 260) / 3;
        dimensionPanel.width = size;
        panelAlgorithm.setPreferredSize(dimensionPanel);
        panelAlgorithm.setSize(dimensionPanel);
        panelSizeKey.setPreferredSize(new Dimension(size * 2, dimensionPanel.height));
        panelSizeKey.setSize(panelSizeKey.getPreferredSize());

        dimensionLabel.width = dimensionPanel.width - 20;
        labelAlgorithm.setPreferredSize(dimensionLabel);
        labelSizeKey.setPreferredSize(new Dimension(dimensionPanel.width * 2 - 20, dimensionLabel.height));

        dimensionComboBox.width = dimensionPanel.width - 20;
        jbcAlgorithms.setPreferredSize(dimensionComboBox);
        panelTypeInputSizeKey.setPreferredSize(new Dimension(dimensionPanel.width * 2 - 20, dimensionComboBox.height));
        panelTypeInputSizeKey.setSize(panelTypeInputSizeKey.getPreferredSize());

        var sizePanel = panelTypeInputSizeKey.getSize();
        jbcSizeKeyHill.setPreferredSize(sizePanel);
        inputSizeKeyShift.setPreferredSize(sizePanel);
        inputSizeKeyVigenere.setPreferredSize(sizePanel);
        inputSizeKeySubstitution.setPreferredSize(sizePanel);
        panelInputSizeAffine.setPreferredSize(sizePanel);

        inputSizeKeyAAffine.setPreferredSize(new Dimension(sizePanel.width / 2 - 50, sizePanel.height));
        inputSizeKeyBAffine.setPreferredSize(inputSizeKeyAAffine.getPreferredSize());
    }

    @Getter
    @Setter
    public static class AlgorithmKey {
        private String name;
        private String size;

        public AlgorithmKey(String name, String size) {
            this.name = name;
            this.size = size;
        }
    }

    static class NumericFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("\\d*")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }
}
