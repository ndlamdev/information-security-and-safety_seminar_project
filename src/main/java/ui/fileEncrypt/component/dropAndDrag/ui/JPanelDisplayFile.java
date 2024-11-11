package main.java.ui.fileEncrypt.component.dropAndDrag.ui;

import main.java.ui.fileEncrypt.component.dropAndDrag.DropAndDragComponent;
import main.java.ui.fileEncrypt.component.label.LabelBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JPanelDisplayFile extends JPanel {
    private JButton jbtDeleteFile;
    private LabelBorder fileLabel;
    private DropAndDragComponent dropAndDragComponent;

    public JPanelDisplayFile(DropAndDragComponent dropAndDragComponent) {
        this.dropAndDragComponent = dropAndDragComponent;
        setOpaque(false);
        this.init();
        this.event();
    }

    private void init() {
        URL iconFile = getClass().getClassLoader().getResource("icon/file.png");
        ImageIcon icon = new ImageIcon(iconFile);
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(icon, SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        this.add(label);

        jbtDeleteFile = new JButton("X");
        this.add(jbtDeleteFile);

        fileLabel = new LabelBorder("");
        fileLabel.setPreferredSize(new Dimension(900, 50));
        this.add(fileLabel);
    }

    private void event() {
        jbtDeleteFile.addActionListener(actionEvent -> {
            dropAndDragComponent.removeFile();
        });
    }

    public void setTextFileLabel(String file) {
        this.fileLabel.setText(file);
    }

    public String getFile() {
        return this.fileLabel.getText().isBlank() ? null : this.fileLabel.getText();
    }
}