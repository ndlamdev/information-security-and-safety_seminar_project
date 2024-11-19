/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:21 PM - 14/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.key;

import com.lamnguyen.helper.FileTransferable;
import com.lamnguyen.helper.IconResizeHelper;
import com.lamnguyen.helper.SettingHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ButtonKey extends JButton {
    private String pathKey;

    public ButtonKey(String pathKey) {
        super();
        this.pathKey = pathKey;
        this.init();
        this.setText(getNameKey());
        this.event();
    }

    private void init() {
        ImageIcon icon = IconResizeHelper.getInstance().initImageIcon("key.png", 20, 20);
        this.setIcon(icon);
        this.setName(pathKey);
        this.setPreferredSize(new Dimension(300, 50));
        this.setFont(new Font("", Font.PLAIN, 20));
        this.setIconTextGap(10);
        this.setHorizontalAlignment(SwingConstants.LEFT);
    }

    private void event() {
        this.setTransferHandler(new ButtonTransferHandler());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JComponent component = (JComponent) e.getSource();
                TransferHandler handler = component.getTransferHandler();
                handler.exportAsDrag(component, e, TransferHandler.COPY);
            }
        });
    }

    private String getNameKey() {
        var indexOf = pathKey.indexOf("/");
        var name = pathKey;
        if (indexOf != -1)
            name = name.substring(indexOf + 1, name.length());

        return name;
    }

    static class ButtonTransferHandler extends TransferHandler {
        @Override
        protected Transferable createTransferable(JComponent c) {
            JButton button = (JButton) c;

            File file = new File(SettingHelper.getInstance().getWorkSpace() + "/" + button.getName());
            if (!file.exists() || file.isDirectory()) return null;

            List<File> fileList = new ArrayList<>();
            fileList.add(file);

            return new FileTransferable(fileList);
        }

        @Override
        public int getSourceActions(JComponent c) {
            return COPY;
        }
    }
}
