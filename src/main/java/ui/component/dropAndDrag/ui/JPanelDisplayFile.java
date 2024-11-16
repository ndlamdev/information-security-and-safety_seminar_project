package main.java.ui.component.dropAndDrag.ui;

import main.java.helper.IconResizeHelper;
import main.java.ui.component.dropAndDrag.DropAndDragComponent;
import main.java.ui.component.label.LabelBorder;

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
        var icon = IconResizeHelper.getInstance().initImageIcon("icon/file.png", 100, 100);
        label = new JLabel(icon, SwingConstants.CENTER);
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
        label.setBorder(BorderFactory.createEmptyBorder((getParent().getHeight() - 180) / 2, 0, 0, 0));
        fileLabel.setPreferredSize(new Dimension(getParent().getWidth() - 100, 50));
    }
}