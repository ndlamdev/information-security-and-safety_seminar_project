/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:29 AM - 10/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.selector;

import com.lamnguyen.security.traditionalCipher.ITraditionalCipher;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class SelectCipherTraditionalAlgorithmComponent extends JPanel implements Observer {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;
    private final Function<Algorithm, Void> onAlgChange;
    private JComboBox<ITraditionalCipher.Algorithms> jcbAlgorithm;
    private JComboBox<ITraditionalCipher.SecureLanguage> jcbLang;
    private JPanel panelAlgorithm, panelLang;
    private Dimension sizePanel, sizeLabel, sizeCombobox;
    private JLabel labelAlgorithm, labelLang;
    private ITraditionalCipher.Algorithms alg;
    private ITraditionalCipher.SecureLanguage lang;

    public SelectCipherTraditionalAlgorithmComponent(Function<Algorithm, Void> onAlgChange) {
        this.onAlgChange = onAlgChange;
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

        jcbAlgorithm = new JComboBox<>(ITraditionalCipher.Algorithms.values()) {{
            addActionListener(actionEvent -> {
                if (alg != jcbAlgorithm.getSelectedItem())
                    onAlgChange.apply(getAlgorithm());
            });
        }};
        labelAlgorithm = new JLabel("Chọn thuật toán!");
        panelAlgorithm = new JPanel() {{
            add(labelAlgorithm);
            add(jcbAlgorithm);
            setOpaque(false);
        }};
        this.add(panelAlgorithm);

        jcbLang = new JComboBox<>(ITraditionalCipher.SecureLanguage.values()) {{
            addActionListener(actionEvent -> {
                if (lang != jcbLang.getSelectedItem())
                    onAlgChange.apply(getAlgorithm());
            });
        }};
        labelLang = new JLabel("Chọn ngôn ngữ!");
        panelLang = new JPanel() {{
            add(labelLang);
            add(jcbLang);
            setOpaque(false);
        }};
        this.add(panelLang);

    }

    public Algorithm getAlgorithm() {
        alg = (ITraditionalCipher.Algorithms) jcbAlgorithm.getSelectedItem();
        lang = (ITraditionalCipher.SecureLanguage) jcbLang.getSelectedItem();
        return new Algorithm(alg, lang);
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

        sizePanel.width = (sizeParent - 270) / 2;
        panelAlgorithm.setPreferredSize(sizePanel);
        panelLang.setPreferredSize(sizePanel);

        sizeLabel.width = sizePanel.width - 100;
        labelAlgorithm.setPreferredSize(sizeLabel);
        labelLang.setPreferredSize(sizeLabel);

        sizeCombobox.width = sizeLabel.width;
        jcbAlgorithm.setPreferredSize(sizeCombobox);
        jcbLang.setPreferredSize(sizeCombobox);
    }


    public record Algorithm(ITraditionalCipher.Algorithms algorithm, ITraditionalCipher.SecureLanguage language) {
    }
}
