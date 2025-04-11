/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:29 AM - 10/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.selector;

import com.lamnguyen.config.HashAlgorithmConfig;
import com.lamnguyen.ui.view.HashPage;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SelectHashAlgorithmComponent extends JPanel implements Observer {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;
    private JComboBox<String> jcbAlgorithm;
    private final HashPage hashFilePage;

    public SelectHashAlgorithmComponent(HashPage hashFilePage) {
        this.hashFilePage = hashFilePage;
        this.setOpaque(false);
        this.init();
        this.event();
    }

    private void event() {
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(25, 10, 20, 20));

        jcbAlgorithm = new JComboBox<>(HashAlgorithmConfig.getInstance().getHashAlgForFile().toArray(String[]::new)) {{
            addActionListener(actionEvent -> {
                hashFilePage.setHashString("");
            });
        }};
        this.add(jcbAlgorithm);
    }

    public String getAlgorithm() {
        return (String) jcbAlgorithm.getSelectedItem();
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
        this.setPreferredSize(new Dimension(sizeParent - 200, 100));
        jcbAlgorithm.setPreferredSize(new Dimension(sizeParent - 250, 50));
    }

    public void setListAlg(List<String> listAlg) {
        jcbAlgorithm.setModel(new DefaultComboBoxModel<>(listAlg.toArray(String[]::new)));
    }
}
