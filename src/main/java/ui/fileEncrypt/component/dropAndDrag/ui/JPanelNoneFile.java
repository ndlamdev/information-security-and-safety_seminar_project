package main.java.ui.fileEncrypt.component.dropAndDrag.ui;

import main.java.helper.FileChooseHelper;
import main.java.helper.IconResizeHelper;
import main.java.ui.fileEncrypt.component.dropAndDrag.DropAndDragComponent;
import main.java.ui.fileEncrypt.config.FontConfig;
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
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class JPanelNoneFile extends JPanel implements Observer {
    private FontConfig fontConfig;
    private DropAndDragComponent dropAndDragComponent;
    private JLabel text;

    public JPanelNoneFile(DropAndDragComponent dropAndDragComponent) {
        this.dropAndDragComponent = dropAndDragComponent;
        fontConfig = FontConfig.getInstance();
        this.setOpaque(false);
        this.init();
        this.event();
    }

    private void init() {
        var icon = IconResizeHelper.getInstance().initImageIcon("icon/download.png", 100, 100);
        JLabel label = new JLabel(icon, SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        this.add(label);

        text = new JLabel("Drop file into here!", SwingConstants.CENTER) {{
            setPreferredSize(new Dimension(1000, 50));
            setFont(fontConfig.getFontImperialScript().deriveFont(30f).deriveFont(Font.BOLD));
        }};
        this.add(text);
    }

    private void event() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                var file = FileChooseHelper.selectFile(FileChooseHelper.SelectFileMode.FILES_ONLY);
                if (file == null) return;
                dropAndDragComponent.selectFile(file.getAbsolutePath());
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        this.setDropTarget(new DropTarget(this, new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {

            }

            @Override
            public void dragOver(DropTargetDragEvent dropTargetDragEvent) {

            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent) {

            }

            @Override
            public void dragExit(DropTargetEvent dropTargetEvent) {

            }

            @Override
            public void drop(DropTargetDropEvent dropTargetDropEvent) {
                dropTargetDropEvent.acceptDrop(DnDConstants.ACTION_COPY);

                if (dropTargetDropEvent.getTransferable().isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    ArrayList<File> droppedFiles;
                    try {
                        droppedFiles = (ArrayList<File>) dropTargetDropEvent.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    } catch (UnsupportedFlavorException e) {
                        JOptionPane.showMessageDialog(null, "Flavor không hổ trợ!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "File bị lỗi!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    File file = droppedFiles.get(0);
                    if (file.isDirectory()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn file!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (file.isFile())
                        dropAndDragComponent.selectFile(file.getAbsolutePath());
                } else
                    JOptionPane.showMessageDialog(null, "File không hổ trợ!", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }));
    }

    @Override
    public void update(Observable observable, Object o) {
        text.setPreferredSize(new Dimension(getParent().getWidth(), 50));
    }
}
