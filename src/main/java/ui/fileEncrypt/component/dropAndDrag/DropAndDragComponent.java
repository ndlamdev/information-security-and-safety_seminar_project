/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:30 AM - 08/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.component.dropAndDrag;

import main.java.ui.fileEncrypt.component.dropAndDrag.ui.JPanelDisplayFile;
import main.java.ui.fileEncrypt.component.dropAndDrag.ui.JPanelNoneFile;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.function.Function;

public class DropAndDragComponent extends JPanel {
    private final int RADIUS = 150;
    private final int STROKE_WIDTH = 2;
    private CardLayout cardLayout;
    private JPanelDisplayFile jPanelDisplayFile;
    private Function<String, Void> onFileChanged;

    public DropAndDragComponent(Function<String, Void> onFileChanged) {
        this.onFileChanged = onFileChanged;
        this.init();
        setOpaque(false);
    }

    private void init() {
        this.setPreferredSize(new Dimension(1000, 400));

        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        this.add(JPanelNoneFile.class.getName(), new JPanelNoneFile(this));

        jPanelDisplayFile = new JPanelDisplayFile(this);
        this.add(JPanelDisplayFile.class.getName(), jPanelDisplayFile);
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
        jPanelDisplayFile.setTextFileLabel(file);
        cardLayout.show(this, JPanelDisplayFile.class.getName());
        onFileChanged.apply(file);
    }

    public void removeFile() {
        cardLayout.show(this, JPanelNoneFile.class.getName());
        onFileChanged.apply(null);
    }

    public String getPathFile() {
        return this.jPanelDisplayFile.getFile();
    }

    public File getFile() {
        String pathFile = getPathFile();
        return pathFile == null ? null : new File(pathFile);
    }
}

