package com.lamnguyen.ui.component.dropAndDrag.ui;

import com.lamnguyen.helper.IconResizeHelper;
import com.lamnguyen.ui.component.dropAndDrag.DropAndDragComponent;
import com.lamnguyen.ui.component.label.LabelBorder;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class JPanelDisplayFile extends JPanel implements Observer {
    private JButton jbtDeleteFile;
    private LabelBorder fileLabel;
    private DropAndDragComponent dropAndDragComponent;
    private JLabel label;

    public JPanelDisplayFile(DropAndDragComponent dropAndDragComponent) {
        this.dropAndDragComponent = dropAndDragComponent;
        setOpaque(false);
        this.init();
        this.event();
    }

    private void init() {
        label = new JLabel("", SwingConstants.CENTER);
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
        reload();
    }

    public void reload() {
        var icon = IconResizeHelper.getInstance().initImageIcon("file.png", getParent().getHeight() / 3, getParent().getHeight() / 3);
        label.setIcon(icon);
        label.setBorder(BorderFactory.createEmptyBorder((getParent().getHeight() - getParent().getHeight() / 3 - 80) / 2, 0, 0, 0));
        fileLabel.setPreferredSize(new Dimension(getParent().getWidth() - 100, 50));
    }
}