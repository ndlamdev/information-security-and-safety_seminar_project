package main.java.ui.component.dropAndDrag.ui;

import main.java.helper.FileChooseHelper;
import main.java.helper.IconResizeHelper;
import main.java.ui.component.dropAndDrag.DropAndDragComponent;
import main.java.ui.config.FontConfig;
import main.java.ui.controller.DropFileAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;


public class JPanelNoneFile extends JPanel implements Observer {
    private FontConfig fontConfig;
    private DropAndDragComponent dropAndDragComponent;
    private JLabel text;
    private JLabel label;

    public JPanelNoneFile(DropAndDragComponent dropAndDragComponent) {
        this.dropAndDragComponent = dropAndDragComponent;
        fontConfig = FontConfig.getInstance();
        this.setOpaque(false);
        this.init();
        this.event();
    }

    private void init() {
        var icon = IconResizeHelper.getInstance().initImageIcon("icon/download.png", 100, 100);
        label = new JLabel(icon, SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        this.add(label);

        text = new JLabel("Drop file into here!", SwingConstants.CENTER) {{
            setPreferredSize(new Dimension(1000, 50));
            setFont(fontConfig.getFontImperialScript().deriveFont(30f).deriveFont(Font.BOLD));
        }};
        this.add(text);
    }

    private void event() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var file = FileChooseHelper.selectFile(FileChooseHelper.SelectFileMode.FILES_ONLY);
                if (file == null) return;
                dropAndDragComponent.selectFile(file.getAbsolutePath());
            }
        });

        this.setDropTarget(new DropTarget(this, new DropFileAdapter(file -> {
            dropAndDragComponent.selectFile(file.getAbsolutePath());
            return null;
        })));
    }

    @Override
    public void update(Observable observable, Object o) {
        label.setBorder(BorderFactory.createEmptyBorder((getParent().getHeight() - 150) / 2, 0, 0, 0));
        text.setPreferredSize(new Dimension(getParent().getWidth(), 50));
    }
}
