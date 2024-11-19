/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:37 PM - 14/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.controller;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

public class DropFileAdapter extends DropTargetAdapter {
    private Function<File, Void> fileProvider;

    public DropFileAdapter(Function<File, Void> fileProvider) {
        this.fileProvider = fileProvider;
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

            if (file.isFile() && fileProvider != null)
                fileProvider.apply(file);
        } else
            JOptionPane.showMessageDialog(null, "File không hổ trợ!", "Error", JOptionPane.ERROR_MESSAGE);

    }
}
