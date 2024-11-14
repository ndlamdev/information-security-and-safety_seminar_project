package main.java.ui.fileEncrypt.component.dropAndDrag.ui;

import main.java.helper.IconResizeHelper;
import main.java.ui.fileEncrypt.component.dropAndDrag.DropAndDragComponent;
import main.java.ui.fileEncrypt.component.label.LabelBorder;
import main.java.ui.fileEncrypt.controller.SubjectSizeController;

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
import java.util.Observable;
import java.util.Observer;

public class JPanelDisplayFile extends JPanel implements Observer {
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
        var icon = IconResizeHelper.getInstance().initImageIcon("icon/file.png", 100, 100);
        JLabel label = new JLabel(icon, SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        this.add(label);

        jbtDeleteFile = new JButton("X");
        this.add(jbtDeleteFile);

        fileLabel = new LabelBorder("");
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

    @Override
    public void update(Observable observable, Object o) {
        fileLabel.setPreferredSize(new Dimension(getParent().getWidth() - 100, 50));
    }
}