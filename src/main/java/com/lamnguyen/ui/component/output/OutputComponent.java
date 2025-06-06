/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:58 AM - 10/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.output;

import com.lamnguyen.ui.component.input.SelectFolderDest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class OutputComponent extends JPanel implements Observer {
    private final int RADIUS = 20;
    private final int STROKE_WIDTH = 2;
    private JTextField jtfFileDest;
    private JLabel jlbFolderDest, jlbExtensionFile;
    private JButton jbtAction;
    private ActionListener actionButtonAction;
    private JLabel label;

    public OutputComponent() {
        this.setOpaque(false);
        this.init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        jlbFolderDest = new SelectFolderDest();
        this.add(jlbFolderDest);

        label = new JLabel( File.separator) {{
            setPreferredSize(new Dimension(20, 50));
            setSize(getPreferredSize());
            setFont(new Font("", Font.PLAIN, 40));
            setHorizontalAlignment(SwingConstants.CENTER);
        }};
        this.add(label);

        jtfFileDest = new JTextField();
        this.add(jtfFileDest);

        jlbExtensionFile = new JLabel("") {{
            setForeground(Color.GRAY);
            setPreferredSize(new Dimension(100, 50));
            setSize(getPreferredSize());
            setFont(new Font("", Font.PLAIN, 30));
        }};
        this.add(jlbExtensionFile);


        jbtAction = new JButton("Mã hóa!") {{
            setPreferredSize(new Dimension(150, 50));
            setSize(getPreferredSize());
        }};
        this.add(jbtAction);
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
        g2.fillRect(20, 0, 130, 28);

        g2.setColor(Color.BLACK);
        g2.drawString("Xuất kết quả!", 30, 15);

        super.paintComponent(g);
    }

    public void setPathFolder(String pathFolder) {
        jlbFolderDest.setText(pathFolder);
    }

    public void setFileName(String fileName) {
        jtfFileDest.setText(fileName);
    }

    public void setExtensionFile(String extensionFile) {
        this.jlbExtensionFile.setText(extensionFile);
    }

    public void setTextButtonAction(String textButtonAction) {
        jbtAction.setText(textButtonAction);
    }

    public void setActionButtonAction(ActionListener actionButtonAction) {
        if (this.actionButtonAction != null)
            jbtAction.removeActionListener(this.actionButtonAction);

        this.actionButtonAction = actionButtonAction;
        jbtAction.addActionListener(actionButtonAction);
    }

    public String getFolderDest() {
        var path = jlbFolderDest.getText();
        return path.isBlank() ? "" : (path + File.separator);
    }

    public String getExtensionFile() {
        return jlbExtensionFile.getText();
    }

    public String getFileDest() {
        return jtfFileDest.getText();
    }

    public String getFullPath() {
        return getFolderDest() + getFileDest() + getExtensionFile();
    }

    @Override
    public void update(Observable observable, Object o) {
        var sizeParent = getParent().getWidth();
        this.setPreferredSize(new Dimension(sizeParent - 200, 110));
        this.setSize(this.getPreferredSize());
        var temp = this.getSize().width - (this.jbtAction.getSize().width + jlbExtensionFile.getSize().width + label.getSize().width + 40 + 10 * 4);
        jlbFolderDest.setPreferredSize(new Dimension(temp / 5 * 3, 50));
        jtfFileDest.setPreferredSize(new Dimension(temp / 5 * 2, 50));
    }
}
