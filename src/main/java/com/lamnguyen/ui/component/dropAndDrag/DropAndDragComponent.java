/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:30 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.dropAndDrag;

import com.lamnguyen.ui.component.dropAndDrag.ui.JPanelDisplayFile;
import com.lamnguyen.ui.component.dropAndDrag.ui.JPanelNoneFile;
import com.lamnguyen.ui.controller.SubjectSizeController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class DropAndDragComponent extends JPanel implements Observer {
    private final int RADIUS = 100;
    private final int STROKE_WIDTH = 2;
    private CardLayout cardLayout;
    private JPanelDisplayFile panelDisplayFile;
    private Function<String, Void> onFileChanged;
    private JPanelNoneFile panelNoneFile;
    private SubjectSizeController sizeController = SubjectSizeController.getInstance();
    private Dimension customSize;

    public DropAndDragComponent(Function<String, Void> onFileChanged) {
        this.onFileChanged = onFileChanged;
        this.init();
        setOpaque(false);
    }

    public DropAndDragComponent() {
        this.init();
        setOpaque(false);
    }

    public DropAndDragComponent(Dimension size) {
        this.customSize = size;
        this.setPreferredSize(customSize);
        this.setSize(customSize);
        this.init();
        setOpaque(false);
    }

    private void init() {
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        panelNoneFile = new JPanelNoneFile(this);
        this.add(JPanelNoneFile.class.getName(), panelNoneFile);
        sizeController.addObserver(panelNoneFile);

        panelDisplayFile = new JPanelDisplayFile(this);
        this.add(JPanelDisplayFile.class.getName(), panelDisplayFile);
        sizeController.addObserver(panelDisplayFile);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.white);
        g2.fillRoundRect(0, 0, getWidth() - STROKE_WIDTH, getHeight() - STROKE_WIDTH, RADIUS, RADIUS);

        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(STROKE_WIDTH));
        g2.drawRoundRect(0, 0, getWidth() - STROKE_WIDTH, getHeight() - STROKE_WIDTH, RADIUS, RADIUS);

        super.paintComponent(g);
    }

    public void selectFile(String file) {
        panelDisplayFile.setTextFileLabel(file);
        cardLayout.show(this, JPanelDisplayFile.class.getName());
        if (onFileChanged != null)
            onFileChanged.apply(file);
    }

    public void removeFile() {
        cardLayout.show(this, JPanelNoneFile.class.getName());
        if (onFileChanged != null)
            onFileChanged.apply(null);
    }

    public String getPathFile() {
        return this.panelDisplayFile.getFile();
    }

    public File getFile() {
        String pathFile = getPathFile();
        return pathFile == null ? null : new File(pathFile);
    }

    @Override
    public void update(Observable observable, Object o) {
        this.setPreferredSize(Objects.requireNonNullElseGet(customSize, () -> new Dimension(getParent().getWidth() - 300, 200)));

        this.setSize(this.getPreferredSize());
    }
}

